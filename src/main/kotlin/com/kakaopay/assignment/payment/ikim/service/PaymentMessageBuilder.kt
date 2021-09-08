package com.kakaopay.assignment.payment.ikim.service

import com.kakaopay.assignment.payment.ikim.support.leftPaddingWithBlank
import com.kakaopay.assignment.payment.ikim.support.rightPaddingWithBlank
import com.kakaopay.assignment.payment.ikim.value.PaymentType

object PaymentMessageBuilder {
    private const val DATA_LENGTH = 4
    private const val TYPE_LENGTH = 10
    private const val UNIQUE_ID_LENGTH = 20

    fun paidWithHeader(uniqueId: String, body: String): String {
        val len = body.length + TYPE_LENGTH + UNIQUE_ID_LENGTH
        return StringBuilder()
            .append(len.leftPaddingWithBlank(DATA_LENGTH))
            .append(PaymentType.PAYMENT.name.rightPaddingWithBlank(TYPE_LENGTH))
            .append(uniqueId)
            .append(body)
            .toString()
    }
}