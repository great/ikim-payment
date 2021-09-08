package com.kakaopay.assignment.payment.ikim.service

import com.kakaopay.assignment.payment.ikim.builder.CardPaymentData
import com.kakaopay.assignment.payment.ikim.controller.parameter.PayRequest
import com.kakaopay.assignment.payment.ikim.controller.parameter.cardInfo
import com.kakaopay.assignment.payment.ikim.controller.parameter.cardPayment
import com.kakaopay.assignment.payment.ikim.support.leftPaddingWithBlank
import com.kakaopay.assignment.payment.ikim.support.leftPaddingWithZero
import com.kakaopay.assignment.payment.ikim.support.rightPaddingWithBlank

object PaymentMessageBodyBuilder {
    private const val DATA_SIZE_CARD = 20
    private const val DATA_SIZE_INSTALLMENT = 2
    private const val DATA_SIZE_AMOUNT = 10
    private const val DATA_SIZE_VAT = 10
    private const val DATA_SIZE_ENCRYPTED = 300
    private val UNIQUE_ID_PLACEHOLDER = " ".repeat(20)
    private val RESERVED = " ".repeat(47)

    fun paidMessageBody(cardData: CardPaymentData): String {
        return StringBuilder()
            .append(cardData.cardNo.rightPaddingWithBlank(DATA_SIZE_CARD))
            .append(cardData.installment.leftPaddingWithZero(DATA_SIZE_INSTALLMENT))
            .append(cardData.expiry)
            .append(cardData.cvc)
            .append(cardData.amount.leftPaddingWithBlank(DATA_SIZE_AMOUNT))
            .append(cardData.vat.leftPaddingWithBlank(DATA_SIZE_VAT))
            .append(UNIQUE_ID_PLACEHOLDER)
            .append(cardData.encryptedCardInfo.rightPaddingWithBlank(DATA_SIZE_ENCRYPTED))
            .append(RESERVED)
            .toString()
    }

    // TODO uniqueId 길이를 검증하도록 해야...
    fun refundData(originalUniqueId: String): String {
        TODO("not yet implemented")
    }
}
