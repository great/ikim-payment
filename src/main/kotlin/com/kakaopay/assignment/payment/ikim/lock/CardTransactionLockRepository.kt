package com.kakaopay.assignment.payment.ikim.lock

import org.springframework.data.repository.CrudRepository

interface CardTransactionLockRepository : CrudRepository<NaiveCardTransaction, String>
