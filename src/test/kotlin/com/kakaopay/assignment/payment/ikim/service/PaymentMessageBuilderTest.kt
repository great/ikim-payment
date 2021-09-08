package com.kakaopay.assignment.payment.ikim.service

import com.kakaopay.assignment.payment.ikim.support.leftPaddingWithBlank
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

// TODO 무엇을 어떻게 테스트하겠다고 하는지 명확하게 보이지 않는다...
class PaymentMessageBuilderTest {
    private val dataLength = 4
    private val typeLength = 10
    private val idLength = 20
    private val id = "id__length__of____20"
    private val body = "body_length_of_17"

    @Test
    fun verifyMessageSizeProperlyAssigned() {
        val message = PaymentMessageBuilder.messageWithHeader(id, body)
        assertThat(message).startsWith((typeLength + idLength + body.length).leftPaddingWithBlank(dataLength))
        assertThat(message.length).isEqualTo(dataLength + typeLength + idLength + body.length)
    }
}
