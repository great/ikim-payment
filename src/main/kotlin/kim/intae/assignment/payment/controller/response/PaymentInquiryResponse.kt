package kim.intae.assignment.payment.controller.response

import kim.intae.assignment.payment.service.PaymentResult

class PaymentInquiryResponse(
    val uniqueId: String,
    val cardNo: String,
    val cardExpiry: String,
    val cvc: String,
    val paymentType: String,
    val amount: Int,
    val vat: Int
) {
    constructor(r: PaymentResult) :
        this(r.uniqueId, r.card.maskedCardNo, r.card.expiry, r.card.cvc, r.paymentType.name, r.amount.amount, r.amount.vat)
}
