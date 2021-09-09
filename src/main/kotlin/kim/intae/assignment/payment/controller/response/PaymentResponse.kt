package kim.intae.assignment.payment.controller.response

import org.springframework.http.HttpStatus

class PaymentResponse(
    val status: HttpStatus,
    val message: String?,
)
