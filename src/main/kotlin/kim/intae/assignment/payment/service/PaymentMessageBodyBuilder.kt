package kim.intae.assignment.payment.service

import kim.intae.assignment.payment.builder.CardPaymentData
import kim.intae.assignment.payment.builder.CardRefundData
import kim.intae.assignment.payment.support.leftPaddingWithBlank
import kim.intae.assignment.payment.support.leftPaddingWithZero
import kim.intae.assignment.payment.support.rightPaddingWithBlank

object PaymentMessageBodyBuilder {
    private const val DATA_SIZE_CARD = 20
    private const val DATA_SIZE_INSTALLMENT = 2
    private const val DATA_SIZE_AMOUNT = 10
    private const val DATA_SIZE_VAT = 10
    private const val DATA_SIZE_ENCRYPTED = 300
    private val UNIQUE_ID_PLACEHOLDER = " ".repeat(20)
    private val RESERVED = " ".repeat(47)

    fun payMessageBody(cardData: CardPaymentData): String {
        return StringBuilder()
            .append(cardData.cardNo.rightPaddingWithBlank(DATA_SIZE_CARD))
            .append(cardData.installment.leftPaddingWithZero(DATA_SIZE_INSTALLMENT))
            .append(cardData.expiry)
            .append(cardData.cvc)
            .append(cardData.amount.leftPaddingWithBlank(DATA_SIZE_AMOUNT))
            .append(cardData.vat.leftPaddingWithBlank(DATA_SIZE_VAT))
            .append(UNIQUE_ID_PLACEHOLDER)
            .append(cardData.encryptedCardInfo.rightPaddingWithBlank(DATA_SIZE_ENCRYPTED))
            .append(RESERVED)
            .toString()
    }

    // TODO uniqueId 길이를 검증하도록 해야...
    fun refundMessageBody(refund: CardRefundData): String {
        return StringBuilder()
            .append(refund.cardNo.rightPaddingWithBlank(DATA_SIZE_CARD))
            .append("00")
            .append(refund.expiry)
            .append(refund.cvc)
            .append(refund.amount.leftPaddingWithBlank(DATA_SIZE_AMOUNT))
            .append(refund.vat.leftPaddingWithBlank(DATA_SIZE_VAT))
            .append(refund.originalUniqueId)
            .append(refund.encryptedCardInfo.rightPaddingWithBlank(DATA_SIZE_ENCRYPTED))
            .append(RESERVED)
            .toString()
    }
}
