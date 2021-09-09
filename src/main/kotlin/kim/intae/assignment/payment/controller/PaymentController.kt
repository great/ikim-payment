package kim.intae.assignment.payment.controller

import kim.intae.assignment.payment.controller.parameter.PayRequest
import kim.intae.assignment.payment.controller.parameter.RefundRequest
import kim.intae.assignment.payment.controller.parameter.cancelAmount
import kim.intae.assignment.payment.controller.parameter.cardInfo
import kim.intae.assignment.payment.controller.parameter.cardPayment
import kim.intae.assignment.payment.controller.response.BaseResponse
import kim.intae.assignment.payment.controller.response.PaymentInquiryResponse
import kim.intae.assignment.payment.service.CardTransactionService
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

        return BaseResponse(service.requestPay(card, payment))
    }

    @PostMapping("/refund")
    fun refund(@RequestBody body: RefundRequest): BaseResponse {
        return BaseResponse(service.requestCancel(body.uniqueId, body.cancelAmount()))
    }

    @GetMapping("/payments/{uniqueId}")
    fun inquire(@PathVariable uniqueId: String): PaymentInquiryResponse {
        return service.inquirePayment(uniqueId).run { PaymentInquiryResponse(this) }
    }
}
