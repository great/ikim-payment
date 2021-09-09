package kim.intae.assignment.payment.value

import kim.intae.assignment.payment.component.EncryptionTool
import kim.intae.assignment.payment.support.isNumeric

class CardInfo(
    val cardNo: String,
    val expiry: String,
    val cvc: String,
) {
    companion object {
        private const val MASKING_RANGE_HEAD = 6
        private const val MASKING_RANGE_TAIL = 3
    }
    init {
        require(cardNo.isNumeric()) { "카드 번호는 숫자 형식이어야 합니다" }
        require(cardNo.length in 10..20 ) { "카드 번호는 10자 이상 20자 이하여야 합니다" }
        require(expiry.isNumeric()) { "카드 유효기간은 숫자 형식이어야 합니다" } // TODO exact formatting
        require(expiry.length == 4) { "카드 유효기간이 MMYY 네 자리인지 확인해 주세요" } // TODO exact formatting
        require(cvc.isNumeric()) { "카드 CVC는 숫자 형식이어야 합니다" }
        require(cvc.length == 3) { "카드 CVC는 3자리 숫자입니다" }
    }

    fun encryptWith(aesTool: EncryptionTool) = aesTool.encrypt("$cardNo|$expiry|$cvc")

    val maskedCardNo by lazy {
        StringBuilder()
            .append(cardNo.substring(0, MASKING_RANGE_HEAD))
            .append("#".repeat(cardNo.length - MASKING_RANGE_HEAD - MASKING_RANGE_TAIL))
            .append(cardNo.substring(cardNo.length - MASKING_RANGE_TAIL))
            .toString()
    }
}
