package kim.intae.assignment.payment.domain.entity

import kim.intae.assignment.payment.builder.CardPaymentData
import kim.intae.assignment.payment.component.EncryptionTool
import kim.intae.assignment.payment.value.CardInfo
import kim.intae.assignment.payment.value.PaymentAmount
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.time.Instant
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

    val installment: Int,

    @CreatedDate
    val createdAt: Instant,

    @LastModifiedDate
    var updatedAt: Instant
) {
    constructor(uniqueId: String, c: CardPaymentData, now: Instant) : this(uniqueId, c.encryptedCardInfo, c.amount, c.vat, c.installment, now, now)

    fun cardInfoUsing(encryptionTool: EncryptionTool): CardInfo {
        val dec = encryptionTool.decrypt(encryptedCard).split("|")
        return CardInfo(dec[0], dec[1], dec[2])
    }

    fun paymentAmount() = PaymentAmount(amount, vat)
}
