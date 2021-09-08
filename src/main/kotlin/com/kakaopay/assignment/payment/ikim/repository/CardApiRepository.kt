package com.kakaopay.assignment.payment.ikim.repository

import com.kakaopay.assignment.payment.ikim.domain.entity.CardPaymentApi
import org.springframework.data.jpa.repository.Lock
import org.springframework.data.repository.CrudRepository
import javax.persistence.LockModeType

interface CardApiRepository : CrudRepository<CardPaymentApi, String>
