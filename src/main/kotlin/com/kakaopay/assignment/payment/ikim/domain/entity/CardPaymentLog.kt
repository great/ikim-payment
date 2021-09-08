package com.kakaopay.assignment.payment.ikim.domain.entity

import com.kakaopay.assignment.payment.ikim.builder.CardPaymentData
import com.kakaopay.assignment.payment.ikim.support.EncryptionTool
import com.kakaopay.assignment.payment.ikim.value.CardInfo
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id

@Entity(name = "card_payment_log")
class CardPaymentLog(
    @Id
    @Column(length = 20)
    val uniqueId: String,

    @Column(length = 300)
    val encryptedCard: String,

    val amount: Int,
    val vat: Int,
    val installment: Int
) {
    constructor(uniqueId: String, c: CardPaymentData) : this(uniqueId, c.encryptedCardInfo, c.amount, c.vat, c.installment)

    fun cardInfoUsing(encryptionTool: EncryptionTool): CardInfo {
        val dec = encryptionTool.decrypt(encryptedCard).split("|")
        return CardInfo(dec[0], dec[1], dec[2])
    }
}
