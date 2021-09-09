package kim.intae.assignment.payment.builder

import kim.intae.assignment.payment.component.EncryptionTool
import kim.intae.assignment.payment.value.CardInfo
import kim.intae.assignment.payment.value.CardPayment

class CardPaymentData(
    card: CardInfo,
    payment: CardPayment,
    private val aesTool: EncryptionTool
) {
    val cardNo: String = card.cardNo
    val expiry: String = card.expiry
    val cvc: String = card.cvc
    val installment: Int = payment.installment
    val amount: Int = payment.amount
    val vat: Int = payment.vat
    val encryptedCardInfo: String by lazy { card.encryptWith(aesTool) }
}
