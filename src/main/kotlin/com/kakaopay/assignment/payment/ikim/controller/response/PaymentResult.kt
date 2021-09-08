package com.kakaopay.assignment.payment.ikim.controller.response

import com.kakaopay.assignment.payment.ikim.value.CardInfo
import com.kakaopay.assignment.payment.ikim.value.PaymentAmount
import com.kakaopay.assignment.payment.ikim.value.PaymentType

class PaymentResult(
    val uniqueId: String,
    val card: CardInfo,
    val paymentType: PaymentType,
    val amount: PaymentAmount,
)
