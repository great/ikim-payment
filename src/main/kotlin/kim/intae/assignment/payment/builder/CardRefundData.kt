package kim.intae.assignment.payment.builder

import kim.intae.assignment.payment.domain.entity.CardPaymentLog
import kim.intae.assignment.payment.value.CardInfo

class CardRefundData(paid: CardPaymentLog, card: CardInfo) {
    val originalUniqueId: String = paid.uniqueId
    val cardNo: String = card.cardNo
    val expiry: String = card.expiry
    val cvc: String = card.cvc
    val amount: Int = paid.amount
    val vat: Int = paid.vat
    val encryptedCardInfo: String = paid.encryptedCard
}
