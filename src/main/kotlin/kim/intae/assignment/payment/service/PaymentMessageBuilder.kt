package kim.intae.assignment.payment.service

import kim.intae.assignment.payment.support.leftPaddingWithBlank
import kim.intae.assignment.payment.support.rightPaddingWithBlank
import kim.intae.assignment.payment.value.PaymentType

object PaymentMessageBuilder {
    private const val DATA_LENGTH = 4
    private const val TYPE_LENGTH = 10
    private const val UNIQUE_ID_LENGTH = 20

    fun messageWithHeader(uniqueId: String, body: String): String {
        val len = body.length + TYPE_LENGTH + UNIQUE_ID_LENGTH
        return StringBuilder()
            .append(len.leftPaddingWithBlank(DATA_LENGTH))
            .append(PaymentType.PAYMENT.name.rightPaddingWithBlank(TYPE_LENGTH))
            .append(uniqueId)
            .append(body)
            .toString()
    }
}
