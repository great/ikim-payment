package kim.intae.assignment.payment.controller.parameter

data class PayRequest(
    val cardNo: String,
    val cardExpirationDigit: String,
    val cvc: String,
    val installment: Int?,
    val amount: Int,
    val vat: Int?
)
