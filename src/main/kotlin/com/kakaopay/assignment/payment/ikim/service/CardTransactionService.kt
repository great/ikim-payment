package com.kakaopay.assignment.payment.ikim.service

import com.kakaopay.assignment.payment.ikim.controller.parameter.PayRequest
import com.kakaopay.assignment.payment.ikim.domain.entity.CardPaymentApi
import com.kakaopay.assignment.payment.ikim.repository.CardApiRepository
import com.kakaopay.assignment.payment.ikim.support.EncryptionTool
import com.kakaopay.assignment.payment.ikim.support.IdGenerationTool
import com.kakaopay.assignment.payment.ikim.value.PaymentType
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
class CardTransactionService(
    private val apiRepository: CardApiRepository,
    private val generator: IdGenerationTool,
    private val encryptionTool: EncryptionTool
) {
    @Transactional
    fun findAll() = apiRepository.findAll()

    @Transactional
    fun requestPayment(request: PayRequest): Pair<String, String> {
        val uniqueId = generator.next()
        val body = PaymentMessageBodyBuilder(encryptionTool).paidMessageBody(request)
        val message = PaymentMessageBuilder.paidWithHeader(uniqueId, body)

        val x = apiRepository.save(CardPaymentApi(uniqueId, PaymentType.PAYMENT, message))
        return uniqueId to x.message
    }
}
