package kim.intae.assignment.payment.controller.parameter

import kim.intae.assignment.payment.value.CardInfo
import kim.intae.assignment.payment.value.CardPayment
import kim.intae.assignment.payment.value.PaymentAmount

fun PayRequest.cardInfo() = CardInfo(this.cardNo, this.cardExpirationDigit, this.cvc)
fun PayRequest.cardPayment() = CardPayment(PaymentAmount(this.amount, this.vat), this.installment)

fun RefundRequest.cancelAmount() = PaymentAmount(this.cancelAmount, this.vat)
