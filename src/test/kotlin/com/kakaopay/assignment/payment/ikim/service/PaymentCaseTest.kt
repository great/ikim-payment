package com.kakaopay.assignment.payment.ikim.service

import com.kakaopay.assignment.payment.ikim.component.FrozenClock
import com.kakaopay.assignment.payment.ikim.domain.entity.CardPaymentApi
import com.kakaopay.assignment.payment.ikim.duel.DummyEncryptionTool
import com.kakaopay.assignment.payment.ikim.duel.DummyIdGenerationTool
import com.kakaopay.assignment.payment.ikim.lock.CardTransactionLockRepository
import com.kakaopay.assignment.payment.ikim.lock.NaiveCardTransaction
import com.kakaopay.assignment.payment.ikim.repository.CardApiRepository
import com.kakaopay.assignment.payment.ikim.repository.CardPaymentLogRepository
import com.kakaopay.assignment.payment.ikim.repository.CardRefundLogRepository
import com.kakaopay.assignment.payment.ikim.value.CardInfo
import com.kakaopay.assignment.payment.ikim.value.CardPayment
import com.kakaopay.assignment.payment.ikim.value.PaymentAmount
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class PaymentCaseTest {
    private val cardNo = "1234567823456789"
    private val expiry = "1225"
    private val cvc = "510"
    private val encrypted = "ThiS_iS_FaKe_eNCrYpTEd_CaRD_anD_ExPirY_ANd_cVC"

    private val card = CardInfo(cardNo, expiry, cvc)
    private val payment = CardPayment(PaymentAmount(10000))
    private val keys = listOf("abcdAbcdAbcdAbcd0001", "abcdAbcdAbcdAbcd0002", "abcdAbcdAbcdAbcd0002")

    private val lockRepository: CardTransactionLockRepository = mock()
    private val apiRepository: CardApiRepository = mock()
    private val paymentLogRepository: CardPaymentLogRepository = mock()
    private val refundLogRepository: CardRefundLogRepository = mock()
    private val encryptionTool = DummyEncryptionTool("$cardNo|$expiry|$cvc", encrypted)
    private val clock = FrozenClock()

    private val newLock = NaiveCardTransaction(cardNo, keys[0])
    private val transaction = CardPaymentApi.pay(keys[0], "dummy_message", clock.instant())

    private val sut = CardTransactionService(
        lockRepository,
        apiRepository,
        paymentLogRepository,
        refundLogRepository,
        DummyIdGenerationTool(keys),
        encryptionTool,
        clock
    )

    @Test
    fun `일반 카드 결제 흐름`() {
        given(lockRepository.save(newLock)).willReturn(newLock)
        given(apiRepository.save(any<CardPaymentApi>())).willReturn(transaction)

        assertThat(sut.requestPay(card, payment))
            .isEqualTo(keys[0] to "dummy_message")

        verify(lockRepository).findById(cardNo)
        verify(lockRepository).save(any())
        verify(apiRepository).save(any())
        verify(paymentLogRepository).save(any())
        verify(lockRepository).delete(any())
    }
}
