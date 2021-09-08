package com.kakaopay.assignment.payment.ikim.value

import kotlin.math.roundToInt

class PaymentAmount(
    val amount: Int,
    vatRaw: Int? = null
) {
    companion object {
        private const val VAT_CALCULATION_BASE = 11.0
    }

    val vat: Int

    init {
        vat = vatRaw ?: (amount / VAT_CALCULATION_BASE).roundToInt()
        require(amount in 100..100000000) { "카드 결제 요청 금액은 최소 100원 부터 최대 1억 원 까지 가능합니다" }
        require(vat in 0..amount) { "부가세는 결제 금액보다 클 수 없습니다" }
    }
}
