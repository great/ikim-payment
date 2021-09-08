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
        require(installment != 1 && installment in 0..12) { "할부 개월수는 0(할부 아님) 또는 2~12만 가능합니다" }
    }
}
