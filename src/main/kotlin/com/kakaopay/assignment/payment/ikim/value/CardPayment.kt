package com.kakaopay.assignment.payment.ikim.value

class CardPayment(
    paymentAmount: PaymentAmount,
    installmentRaw: Int?
) {
    val amount = paymentAmount.amount
    val vat = paymentAmount.vat
    val installment: Int

    init {
        installment = installmentRaw ?: 0
        require(installment in 0..12) { "할부 개월수는 0(할부 아님) 또는 1~12만 가능합니다" }
    }
}
