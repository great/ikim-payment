package com.kakaopay.assignment.payment.ikim.controller.parameter

class RefundRequest(
    val uniqueId: String,
    val cancelAmount: Int,
    val vat: Int?,
)
