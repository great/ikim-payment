package com.kakaopay.assignment.payment.ikim.value

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class MaskedCardNoTest {
    val sut = CardInfo("1234567812345678", "1225", "689")

    @Test
    fun `앞 6자리, 뒤 3자리를 빼고 #로 마스킹 처리`() {
        assertThat(sut.maskedCardNo).isEqualTo("123456#######678")
    }
}
