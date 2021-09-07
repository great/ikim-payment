package com.kakaopay.assignment.payment.ikim.support

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class PaddingFunctionsTest {
    @Test
    fun `숫자 왼쪽을 공백으로 채운다`() {
        assertThat(111.leftPaddingWithBlank(10)).isEqualTo("       111")
    }

    @Test
    fun `숫자 왼쪽을 0으로 채운다`() {
        assertThat(1234.leftPaddingWithZero(10)).isEqualTo("0000001234")
    }

    @Test
    fun `숫자 오른쪽을 공백으로 채운다`() {
        assertThat(111.rightPaddingWithBlank(10)).isEqualTo("111       ")
    }

    @Test
    fun `문자열 오른쪽을 공백으로 채운다`() {
        assertThat("Apple".rightPaddingWithBlank(10)).isEqualTo("Apple     ")
    }
}
