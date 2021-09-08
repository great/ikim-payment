package com.kakaopay.assignment.payment.ikim.value

import com.kakaopay.assignment.payment.ikim.value.PaymentAmount
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.catchThrowable
import org.junit.jupiter.api.Test

class PaymentAmountTest {
    @Test
    fun `경계 조건 - 결제 금액 100원은 가능 & 부가세 xx원`() {
        val x = PaymentAmount(100)

        assertThat(x.amount).isEqualTo(100)
        assertThat(x.vat).isEqualTo(9)
    }

    @Test
    fun `경계 조건 - 결제 금액 1억 원은 가능 & 부가세 xx원`() {
        val x = PaymentAmount(100000000)

        assertThat(x.amount).isEqualTo(100000000)
        assertThat(x.vat).isEqualTo(9090909)
    }

    @Test
    fun `결제 금액은 100원 이상이어야 한다`() {
        val ex = catchThrowable { PaymentAmount(99) }
        assertThat(ex.message).isEqualTo("카드 결제 요청 금액은 최소 100원 부터 최대 1억 원 까지 가능합니다")
    }

    @Test
    fun `결제 금액은 1억원 이하여야 한다`() {
        val ex = catchThrowable { PaymentAmount(100000001) }
        assertThat(ex.message).isEqualTo("카드 결제 요청 금액은 최소 100원 부터 최대 1억 원 까지 가능합니다")
    }

    @Test
    fun `부가세는 결제 금액보다 클 수 없다`() {
        val ex = catchThrowable { PaymentAmount(1000, 2001) }
        assertThat(ex.message).isEqualTo("부가세는 결제 금액보다 클 수 없습니다")
    }
}
