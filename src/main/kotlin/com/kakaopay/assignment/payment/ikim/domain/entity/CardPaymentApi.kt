package com.kakaopay.assignment.payment.ikim.domain.entity

import com.kakaopay.assignment.payment.ikim.value.PaymentType
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
    val message: String
) {
    companion object {
        fun pay(uniqueId: String, message: String) = CardPaymentApi(uniqueId, PaymentType.PAYMENT, message)
    }
}
