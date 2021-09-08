package com.kakaopay.assignment.payment.ikim.controller.parameter

import com.kakaopay.assignment.payment.ikim.value.CardInfo
import com.kakaopay.assignment.payment.ikim.value.CardPayment
import com.kakaopay.assignment.payment.ikim.value.PaymentAmount

fun PayRequest.cardInfo() = CardInfo(this.cardNo, this.cardExpirationDigit, this.cvc)
fun PayRequest.cardPayment() = CardPayment(PaymentAmount(this.amount, this.vat), this.installment)

fun RefundRequest.cancelAmount() = PaymentAmount(this.cancelAmount, this.vat)
