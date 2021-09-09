package kim.intae.assignment.payment.lock

import org.springframework.data.repository.CrudRepository

interface CardTransactionLockRepository : CrudRepository<NaiveCardTransaction, String>
