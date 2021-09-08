package com.kakaopay.assignment.payment.ikim.value

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class PaymentAmountVatTest {
    @Test
    fun `1000원의 부가가치세는 91원`() {
        assertThat(PaymentAmount(1000).vat).isEqualTo(91)
    }

    @Test
    fun `10000원의 부가가치세는 909원`() {
        assertThat(PaymentAmount(10000).vat).isEqualTo(909)
    }
}
