package com.kakaopay.assignment.payment.ikim.value

class PaymentAmount(
    val amount: Int,
    val vat: Int
) {
    constructor(amount: Int) : this(amount, amount / 11)
}
