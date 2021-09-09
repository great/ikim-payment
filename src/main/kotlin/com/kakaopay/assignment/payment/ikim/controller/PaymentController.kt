package com.kakaopay.assignment.payment.ikim.controller

import com.kakaopay.assignment.payment.ikim.controller.parameter.PayRequest
import com.kakaopay.assignment.payment.ikim.controller.parameter.RefundRequest
import com.kakaopay.assignment.payment.ikim.controller.parameter.cancelAmount
import com.kakaopay.assignment.payment.ikim.controller.parameter.cardInfo
import com.kakaopay.assignment.payment.ikim.controller.parameter.cardPayment
import com.kakaopay.assignment.payment.ikim.controller.response.BaseResponse
import com.kakaopay.assignment.payment.ikim.controller.response.PaymentInquiryResponse
import com.kakaopay.assignment.payment.ikim.service.CardTransactionService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class PaymentController(
    private val service: CardTransactionService
) {
    @PostMapping("/pay")
    fun pay(@RequestBody body: PayRequest): BaseResponse {
        val card = body.cardInfo()
        val payment = body.cardPayment()

        val (x, y) = service.requestPay(card, payment)
        return BaseResponse("$x", "$y")
    }

    @PostMapping("/refund")
    fun refund(@RequestBody body: RefundRequest): BaseResponse {
        val (x, y) = service.requestCancel(body.uniqueId, body.cancelAmount())
        return BaseResponse("$x", "$y")
    }

    @GetMapping("/payments/{uniqueId}")
    fun inquire(@PathVariable uniqueId: String): PaymentInquiryResponse {
        return service.inquirePayment(uniqueId).run { PaymentInquiryResponse(this) }
    }
}
