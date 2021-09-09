package kim.intae.assignment.payment.controller.parameter

class RefundRequest(
    val uniqueId: String,
    val cancelAmount: Int,
    val vat: Int?,
)
