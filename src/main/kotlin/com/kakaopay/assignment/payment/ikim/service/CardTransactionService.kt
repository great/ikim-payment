package com.kakaopay.assignment.payment.ikim.service

import com.kakaopay.assignment.payment.ikim.builder.CardPaymentData
import com.kakaopay.assignment.payment.ikim.builder.CardRefundData
import com.kakaopay.assignment.payment.ikim.component.EncryptionTool
import com.kakaopay.assignment.payment.ikim.component.IdGenerationTool
import com.kakaopay.assignment.payment.ikim.domain.entity.CardPaymentApi
import com.kakaopay.assignment.payment.ikim.domain.entity.CardPaymentLog
import com.kakaopay.assignment.payment.ikim.domain.entity.CardRefundLog
import com.kakaopay.assignment.payment.ikim.lock.CardTransactionLock
import com.kakaopay.assignment.payment.ikim.lock.CardTransactionLockRepository
import com.kakaopay.assignment.payment.ikim.repository.CardApiRepository
import com.kakaopay.assignment.payment.ikim.repository.CardPaymentLogRepository
import com.kakaopay.assignment.payment.ikim.repository.CardRefundLogRepository
import com.kakaopay.assignment.payment.ikim.value.CardInfo
import com.kakaopay.assignment.payment.ikim.value.CardPayment
import com.kakaopay.assignment.payment.ikim.value.PaymentAmount
import com.kakaopay.assignment.payment.ikim.value.PaymentType
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.time.Clock
import javax.transaction.Transactional

@Service
class CardTransactionService(
    private val lockRepository: CardTransactionLockRepository,
    private val apiRepository: CardApiRepository,
    private val paymentLogRepository: CardPaymentLogRepository,
    private val refundLogRepository: CardRefundLogRepository,
    private val generator: IdGenerationTool,
    private val encryptionTool: EncryptionTool,
    private val clock: Clock
) {
    @Transactional
    fun requestPay(card: CardInfo, payment: CardPayment): Pair<String, String> {
        val uniqueId = generator.next()
        val lock = CardTransactionLock(lockRepository)
        val tx = lock.acquire(card.cardNo, uniqueId)

        try {
            val cardData = CardPaymentData(card, payment, encryptionTool)
            val now = clock.instant()

            val body = PaymentMessageBodyBuilder.payMessageBody(cardData)
            val message = PaymentMessageBuilder.messageWithHeader(uniqueId, body)
            val transaction = apiRepository.save(CardPaymentApi.pay(uniqueId, message, now))
                .also { paymentLogRepository.save(CardPaymentLog(uniqueId, cardData, now)) }

            return uniqueId to transaction.message
        } finally {
            lock.release(tx)
        }
    }

    @Transactional
    fun requestCancel(originalUniqueId: String, cancelAmount: PaymentAmount): Pair<String, String> {
        val uniqueId = generator.next()
        val now = clock.instant()

        val canceled = refundLogRepository.findByPaidUniqueId(originalUniqueId)
        if (canceled?.paidUniqueId == originalUniqueId) {
            throw IllegalArgumentException("이미 취소한 거래입니다")
        }

        val paid = paymentLogRepository.findByIdOrNull(originalUniqueId)
        if (cancelAmount != paid?.paymentAmount()) {
            throw IllegalArgumentException("취소 요청한 금액/부가세가 결제 정보와 일치하지 않습니다.")
        }

        val lock = CardTransactionLock(lockRepository)
        val tx = lock.acquire(paid.cardInfoUsing(encryptionTool).cardNo, uniqueId)

        try {
            val refund = CardRefundData(paid, paid.cardInfoUsing(encryptionTool))
            val body = PaymentMessageBodyBuilder.refundMessageBody(refund)
            val message = PaymentMessageBuilder.messageWithHeader(uniqueId, body)
            val transaction = apiRepository.save(CardPaymentApi.refund(uniqueId, message, now))
                .also { refundLogRepository.save(CardRefundLog(uniqueId, paid.uniqueId, now, now)) }

            return uniqueId to transaction.message
        } finally {
            lock.release(tx)
        }
    }

    @Transactional
    fun inquirePayment(uniqueId: String): PaymentResult {
        val paid = paymentLogRepository.findByIdOrNull(uniqueId) ?: throw IllegalArgumentException("존재하지 않는 거래입니다.")
        val refunded = refundLogRepository.findByPaidUniqueId(uniqueId)

        return PaymentResult(
            paid.uniqueId,
            paid.cardInfoUsing(encryptionTool),
            if (refunded == null) PaymentType.PAYMENT else PaymentType.CANCEL,
            paid.paymentAmount()
        )
    }
}
