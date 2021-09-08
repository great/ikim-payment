package com.kakaopay.assignment.payment.ikim.value

import com.kakaopay.assignment.payment.ikim.support.EncryptionTool
import com.kakaopay.assignment.payment.ikim.support.isNumeric

class CardInfo(
    val cardNo: String,
    val expiry: String,
    val cvc: String,
) {
    init {
        require(cardNo.isNumeric()) { "카드 번호는 숫자 형식이어야 합니다" }
        require(cardNo.length in 10..20 ) { "카드 번호는 10자 이상 20자 이하여야 합니다" }
        require(expiry.isNumeric()) { "카드 유효기간은 숫자 형식이어야 합니다" } // TODO exact formatting
        require(expiry.length == 4) { "카드 유효기간이 MMYY 네 자리인지 확인해 주세요" } // TODO exact formatting
        require(cvc.isNumeric()) { "카드 CVC는 숫자 형식이어야 합니다" }
        require(cvc.length == 3) { "카드 CVC는 3자리 숫자입니다" }
    }

    fun encryptWith(aesTool: EncryptionTool) = aesTool.encrypt("$cardNo|$expiry|$cvc")
}
