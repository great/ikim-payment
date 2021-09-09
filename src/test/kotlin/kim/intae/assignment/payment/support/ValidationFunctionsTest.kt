package kim.intae.assignment.payment.support

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class ValidationFunctionsTest {
    @Test
    fun `알파벳 + 숫자만 포함한 문자열`() {
        assertThat("12345abcd7809".isAlphaNumeric()).isTrue
    }

    @Test
    fun `알파벳 + 숫자 외 특수문자 등을 포함한 문자열`() {
        assertThat("1234-5abc_d780@9".isAlphaNumeric()).isFalse
    }

    @Test
    fun `숫자로만 구성한 문자열`() {
        assertThat("1234234534564567".isNumeric()).isTrue
    }

    @Test
    fun `숫자 외 다른 요소가 섞인 문자열`() {
        assertThat("1234a2345b34564567c".isNumeric()).isFalse
    }
}
