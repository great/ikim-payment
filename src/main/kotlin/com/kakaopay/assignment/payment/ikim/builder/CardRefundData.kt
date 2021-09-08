package com.kakaopay.assignment.payment.ikim.builder

import com.kakaopay.assignment.payment.ikim.domain.entity.CardPaymentLog
import com.kakaopay.assignment.payment.ikim.value.CardInfo

class CardRefundData(paid: CardPaymentLog, card: CardInfo) {
    val originalUniqueId: String = paid.uniqueId
    val cardNo: String = card.cardNo
    val expiry: String = card.expiry
    val cvc: String = card.cvc
    val amount: Int = paid.amount
    val vat: Int = paid.vat
    val encryptedCardInfo: String = paid.encryptedCard
}
