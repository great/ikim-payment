package com.kakaopay.assignment.payment.ikim.controller

import com.kakaopay.assignment.payment.ikim.controller.parameter.PayRequest
import com.kakaopay.assignment.payment.ikim.controller.parameter.RefundRequest
import com.kakaopay.assignment.payment.ikim.controller.response.BaseResponse
import com.kakaopay.assignment.payment.ikim.controller.response.PaymentResult
import com.kakaopay.assignment.payment.ikim.value.Card
import com.kakaopay.assignment.payment.ikim.value.PaymentAmount
import com.kakaopay.assignment.payment.ikim.value.PaymentType
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class PaymentController {
    @PostMapping("/pay")
    fun pay(@RequestBody body: PayRequest): BaseResponse {
        return BaseResponse("abcd", "XXXXXXXXXXXXXYYYYYZZZZPAY")
    }

    @PostMapping("/refund")
    fun refund(@RequestBody body: RefundRequest): BaseResponse {
        return BaseResponse("abcd", "XXXXXXXXXXXXXYYYYYZZZZREFUND")
    }

    @GetMapping("/payments/{uniqueId}")
    fun inquire(@PathVariable uniqueId: String): PaymentResult {
        return PaymentResult(
            uniqueId,
            Card("1234567812345678", "1225", "510"),
            PaymentType.PAID,
            PaymentAmount(1000),
        )
    }
}
