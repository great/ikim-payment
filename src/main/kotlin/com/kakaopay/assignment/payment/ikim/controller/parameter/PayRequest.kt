package com.kakaopay.assignment.payment.ikim.controller.parameter

data class PayRequest(
    val cardNo: String,
    val cardExpirationDigit: String,
    val cvc: String,
    val installment: String,
    val amount: Int,
    val vat: Int?
)
