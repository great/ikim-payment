package com.kakaopay.assignment.payment.ikim.lock

class CardTransactionLock(private val repository: CardTransactionLockRepository) {
    fun acquire(cardNo: String, uniqueId: String): NaiveCardTransaction {
        val lock = repository.findById(cardNo)
            .orElseGet { repository.save(NaiveCardTransaction(cardNo, uniqueId)) }
        require(lock.uniqueId == uniqueId) { "다른 거래 건을 처리 중이니 잠시 후 다시 시도하시기 바랍니다." }

        return lock
    }

    fun release(lock: NaiveCardTransaction) {
        repository.delete(lock)
    }
}
