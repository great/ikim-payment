package kim.intae.assignment.payment.service

import kim.intae.assignment.payment.value.CardInfo
import kim.intae.assignment.payment.value.PaymentAmount
import kim.intae.assignment.payment.value.PaymentType

class PaymentResult(
    val uniqueId: String,
    val card: CardInfo,
    val paymentType: PaymentType,
    val amount: PaymentAmount,
)
