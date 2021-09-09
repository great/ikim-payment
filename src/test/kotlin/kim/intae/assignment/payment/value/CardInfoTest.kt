package kim.intae.assignment.payment.value

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.catchThrowable
import org.junit.jupiter.api.Test

class CardInfoTest {
    @Test
    fun `invalid card number`() {
        val ex = catchThrowable { cardWithNum("1234abcd5678efgh9012",) }

        assertThat(ex.message).isEqualTo("카드 번호는 숫자 형식이어야 합니다")
    }

    @Test
    fun `card number is too short`() {
        val ex = catchThrowable { cardWithNum("12345678") }

        assertThat(ex.message).isEqualTo("카드 번호는 10자 이상 20자 이하여야 합니다")
    }

    @Test
    fun `card number is too long`() {
        val ex = catchThrowable { cardWithNum("123456781234567812345678") }

        assertThat(ex.message).isEqualTo("카드 번호는 10자 이상 20자 이하여야 합니다")
    }

    @Test
    fun `invalid expiry`() {
        val ex = catchThrowable { cardWithExpiry("111") }

        assertThat(ex.message).isEqualTo("카드 유효기간이 MMYY 네 자리인지 확인해 주세요")
    }

    @Test
    fun `invalid alphanumeric expiry`() {
        val ex = catchThrowable { cardWithExpiry("1a2b") }

        assertThat(ex.message).isEqualTo("카드 유효기간은 숫자 형식이어야 합니다")
    }

    @Test
    fun `invalid cvc`() {
        val ex = catchThrowable { cardWithCvc("1111") }

        assertThat(ex.message).isEqualTo("카드 CVC는 3자리 숫자입니다")
    }

    @Test
    fun `invalid alphanumeric cvc`() {
        val ex = catchThrowable { cardWithCvc("1x3") }

        assertThat(ex.message).isEqualTo("카드 CVC는 숫자 형식이어야 합니다")
    }

    private fun cardWithNum(cardNo: String) = CardInfo(cardNo, "1225", "690")
    private fun cardWithExpiry(exp: String) = CardInfo("1234567812345678", exp, "690")
    private fun cardWithCvc(cvc: String) = CardInfo("1234567812345678", "1225", cvc)
}
