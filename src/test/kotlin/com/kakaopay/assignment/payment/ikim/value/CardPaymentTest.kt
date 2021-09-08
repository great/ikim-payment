package com.kakaopay.assignment.payment.ikim.value

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.catchThrowable
import org.junit.jupiter.api.Test

class CardPaymentTest {
    @Test
    fun `할부 개월수 0은 가능 (=일시불)`() {
        assertThat(installmentSpecifiedCardPay(0).installment).isEqualTo(0)
    }

    @Test
    fun `할부 개월수는 12까지 가능`() {
        assertThat(installmentSpecifiedCardPay(12).installment).isEqualTo(12)
    }

    @Test
    fun `할부 개월수는 0보다 커야 한다`() {
        val ex = catchThrowable { installmentSpecifiedCardPay(-1) }

        assertThat(ex.message).isEqualTo("할부 개월수는 0(할부 아님) 또는 2~12만 가능합니다")
    }

    @Test
    fun `1 개월 할부는 안 돼야 하지 않나`() { // TODO 확인 필요
        val ex = catchThrowable { installmentSpecifiedCardPay(1) }

        assertThat(ex.message).isEqualTo("할부 개월수는 0(할부 아님) 또는 2~12만 가능합니다")
    }

    @Test
    fun `할부 개월수는 12보다 클 수 없다`() {
        val ex = catchThrowable { installmentSpecifiedCardPay(13) }

        assertThat(ex.message).isEqualTo("할부 개월수는 0(할부 아님) 또는 2~12만 가능합니다")
    }

    private fun installmentSpecifiedCardPay(installment: Int) = CardPayment(PaymentAmount(50000), installment)
}
