package com.kakaopay.assignment.payment.ikim.service

import com.kakaopay.assignment.payment.ikim.builder.CardPaymentData
import com.kakaopay.assignment.payment.ikim.builder.CardRefundData
import com.kakaopay.assignment.payment.ikim.domain.entity.CardPaymentApi
import com.kakaopay.assignment.payment.ikim.domain.entity.CardPaymentLog
import com.kakaopay.assignment.payment.ikim.domain.entity.CardRefundLog
import com.kakaopay.assignment.payment.ikim.repository.CardApiRepository
import com.kakaopay.assignment.payment.ikim.repository.CardPaymentLogRepository
import com.kakaopay.assignment.payment.ikim.repository.CardRefundLogRepository
import com.kakaopay.assignment.payment.ikim.support.EncryptionTool
import com.kakaopay.assignment.payment.ikim.support.IdGenerationTool
import com.kakaopay.assignment.payment.ikim.value.CardInfo
import com.kakaopay.assignment.payment.ikim.value.CardPayment
import com.kakaopay.assignment.payment.ikim.value.PaymentAmount
import com.kakaopay.assignment.payment.ikim.value.PaymentType
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
class CardTransactionService(
    private val apiRepository: CardApiRepository,
    private val paymentLogRepository: CardPaymentLogRepository,
    private val refundLogRepository: CardRefundLogRepository,
    private val generator: IdGenerationTool,
    private val encryptionTool: EncryptionTool
) {
    @Transactional
    fun requestPay(card: CardInfo, payment: CardPayment): Pair<String, String> {
        val cardData = CardPaymentData(card, payment, encryptionTool)
        val uniqueId = generator.next()

        val body = PaymentMessageBodyBuilder.payMessageBody(cardData)
        val message = PaymentMessageBuilder.messageWithHeader(uniqueId, body)
        val x = apiRepository.save(CardPaymentApi(uniqueId, PaymentType.PAYMENT, message))
            .also { paymentLogRepository.save(CardPaymentLog(uniqueId, cardData)) }

        return uniqueId to x.message
    }

    @Transactional
    fun requestCancel(originalUniqueId: String, cancelAmount: PaymentAmount): Pair<String, String> {
        val uniqueId = generator.next()
        val canceled = refundLogRepository.findByPaidUniqueId(originalUniqueId)
        if (canceled?.paidUniqueId == originalUniqueId) {
            throw IllegalStateException("이미 취소한 거래입니다")
        }

        val paid = paymentLogRepository.findByIdOrNull(originalUniqueId)

        if (cancelAmount != paid?.paymentAmount()) {
            throw IllegalArgumentException("취소 요청한 금액/부가세가 결제 정보와 일치하지 않습니다.")
        }

        val refund = CardRefundData(paid, paid.cardInfoUsing(encryptionTool))
        val body = PaymentMessageBodyBuilder.refundMessageBody(refund)
        val message = PaymentMessageBuilder.messageWithHeader(uniqueId, body)
        val x = apiRepository.save(CardPaymentApi(uniqueId, PaymentType.CANCEL, message))
            .also { refundLogRepository.save(CardRefundLog(uniqueId, paid.uniqueId)) }

        return uniqueId to x.message
    }
}
