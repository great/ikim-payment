package com.kakaopay.assignment.payment.ikim.controller.response

import org.springframework.http.HttpStatus

class PaymentResponse(
    val status: HttpStatus,
    val message: String?,
)
