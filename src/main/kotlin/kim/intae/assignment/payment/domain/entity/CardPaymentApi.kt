package kim.intae.assignment.payment.domain.entity

import kim.intae.assignment.payment.value.PaymentType
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.time.Instant
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.Id

@Entity(name = "card_payment_api")
class CardPaymentApi(
    @Id
    @Column(length = 20)
    val uniqueId: String,

    @Enumerated(value = EnumType.STRING)
    @Column(length = 10)
    val paymentType: PaymentType,

    @Column(length = 450)
    val message: String,

    @CreatedDate
    val createdAt: Instant,

    @LastModifiedDate
    var updatedAt: Instant
) {
    companion object {
        fun pay(uniqueId: String, message: String, now: Instant) = CardPaymentApi(uniqueId, PaymentType.PAYMENT, message, now, now)
        fun refund(uniqueId: String, message: String, now: Instant) = CardPaymentApi(uniqueId, PaymentType.CANCEL, message, now, now)
    }
}
