package com.kakaopay.assignment.payment.ikim.service

import com.kakaopay.assignment.payment.ikim.builder.CardPaymentData
import com.kakaopay.assignment.payment.ikim.domain.entity.CardPaymentApi
import com.kakaopay.assignment.payment.ikim.domain.entity.CardPaymentLog
import com.kakaopay.assignment.payment.ikim.repository.CardApiRepository
import com.kakaopay.assignment.payment.ikim.repository.CardPaymentLogRepository
import com.kakaopay.assignment.payment.ikim.support.EncryptionTool
import com.kakaopay.assignment.payment.ikim.support.IdGenerationTool
import com.kakaopay.assignment.payment.ikim.value.CardInfo
import com.kakaopay.assignment.payment.ikim.value.CardPayment
import com.kakaopay.assignment.payment.ikim.value.PaymentType
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
class CardTransactionService(
    private val apiRepository: CardApiRepository,
    private val paymentLogRepository: CardPaymentLogRepository,
    private val generator: IdGenerationTool,
    private val encryptionTool: EncryptionTool
) {
    @Transactional
    fun requestPay(card: CardInfo, payment: CardPayment): Pair<String, String> {
        val cardData = CardPaymentData(card, payment, encryptionTool)
        val uniqueId = generator.next()

        val body = PaymentMessageBodyBuilder.paidMessageBody(cardData)
        val message = PaymentMessageBuilder.paidWithHeader(uniqueId, body)
        val x = apiRepository.save(CardPaymentApi(uniqueId, PaymentType.PAYMENT, message))
            .also { paymentLogRepository.save(CardPaymentLog(uniqueId, cardData)) }

        return uniqueId to x.message
    }
}
