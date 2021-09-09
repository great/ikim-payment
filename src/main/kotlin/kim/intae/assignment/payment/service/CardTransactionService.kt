package kim.intae.assignment.payment.service

import kim.intae.assignment.payment.builder.CardPaymentData
import kim.intae.assignment.payment.builder.CardRefundData
import kim.intae.assignment.payment.component.EncryptionTool
import kim.intae.assignment.payment.component.IdGenerationTool
import kim.intae.assignment.payment.domain.entity.CardPaymentApi
import kim.intae.assignment.payment.domain.entity.CardPaymentLog
import kim.intae.assignment.payment.domain.entity.CardRefundLog
import kim.intae.assignment.payment.lock.CardTransactionLock
import kim.intae.assignment.payment.lock.CardTransactionLockRepository
import kim.intae.assignment.payment.repository.CardApiRepository
import kim.intae.assignment.payment.repository.CardPaymentLogRepository
import kim.intae.assignment.payment.repository.CardRefundLogRepository
import kim.intae.assignment.payment.value.CardInfo
import kim.intae.assignment.payment.value.CardPayment
import kim.intae.assignment.payment.value.PaymentAmount
import kim.intae.assignment.payment.value.PaymentType
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
    fun requestPay(card: CardInfo, payment: CardPayment): BaseResult {
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

            return BaseResult(uniqueId, transaction.message)
        } finally {
            lock.release(tx)
        }
    }

    @Transactional
    fun requestCancel(originalUniqueId: String, cancelAmount: PaymentAmount): BaseResult {
        val uniqueId = generator.next()
        val now = clock.instant()

        val canceled = refundLogRepository.findByPaidUniqueId(originalUniqueId)
        if (canceled?.paidUniqueId == originalUniqueId) {
            throw IllegalArgumentException("이미 취소한 거래입니다")
        }

        val paid = paymentLogRepository.findByIdOrNull(originalUniqueId) ?: throw IllegalArgumentException("존재하지 않는 거래입니다.")
        if (cancelAmount != paid.paymentAmount()) {
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

            return BaseResult(uniqueId, transaction.message)
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
