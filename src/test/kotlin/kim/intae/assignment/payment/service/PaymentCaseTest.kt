package kim.intae.assignment.payment.service

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.never
import com.nhaarman.mockitokotlin2.verify
import kim.intae.assignment.payment.component.FrozenClock
import kim.intae.assignment.payment.domain.entity.CardPaymentApi
import kim.intae.assignment.payment.duel.DummyEncryptionTool
import kim.intae.assignment.payment.duel.DummyIdGenerationTool
import kim.intae.assignment.payment.lock.CardTransactionLockRepository
import kim.intae.assignment.payment.lock.NaiveCardTransaction
import kim.intae.assignment.payment.repository.CardApiRepository
import kim.intae.assignment.payment.repository.CardPaymentLogRepository
import kim.intae.assignment.payment.repository.CardRefundLogRepository
import kim.intae.assignment.payment.value.CardInfo
import kim.intae.assignment.payment.value.CardPayment
import kim.intae.assignment.payment.value.PaymentAmount
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.catchThrowable
import org.junit.jupiter.api.Test
import java.util.Optional

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
            .isEqualTo(BaseResult(keys[0], "dummy_message"))

        verify(lockRepository).findById(cardNo)
        verify(lockRepository).save(any())
        verify(apiRepository).save(any())
        verify(paymentLogRepository).save(any())
        verify(lockRepository).delete(any())
    }

    @Test
    fun `중복 결제 방지`() {
        val onGoingTransaction = NaiveCardTransaction(cardNo, "onGoingTxKey00000001")
        given(lockRepository.findById(cardNo)).willReturn(Optional.of(onGoingTransaction))

        val ex = catchThrowable { sut.requestPay(card, payment) }

        assertThat(ex.message).isEqualTo("다른 거래 건을 처리 중이니 잠시 후 다시 시도하시기 바랍니다.")

        verify(lockRepository).findById(cardNo)
        verify(lockRepository, never()).save(any())
        verify(apiRepository, never()).save(any())
        verify(paymentLogRepository, never()).save(any())
        verify(lockRepository, never()).delete(any())
    }
}
