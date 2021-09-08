package com.kakaopay.assignment.payment.ikim.value

class PaymentAmount(
    val amount: Int,
    vatRaw: Int? = null
) {
    val vat: Int

    init {
        vat = vatRaw ?: (amount / 11)
        require(amount in 100..100000000) { "카드 결제 요청 금액은 최소 100원 부터 최대 1억 원 까지 가능합니다" }
        require(vat in 0..amount) { "부가세는 결제 금액보다 클 수 없습니다" }
    }
}
