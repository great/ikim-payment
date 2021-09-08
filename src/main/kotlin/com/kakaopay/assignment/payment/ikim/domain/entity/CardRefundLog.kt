package com.kakaopay.assignment.payment.ikim.domain.entity

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id

@Entity(name = "card_payment_log")
class CardRefundLog(
    @Id
    @Column(length = 20)
    val uniqueId: String,

    @Column(length = 20)
    val paidUniqueId: String,
)
