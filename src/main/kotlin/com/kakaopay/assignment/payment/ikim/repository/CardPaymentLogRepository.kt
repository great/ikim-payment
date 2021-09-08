package com.kakaopay.assignment.payment.ikim.repository

import com.kakaopay.assignment.payment.ikim.domain.entity.CardPaymentLog
import org.springframework.data.repository.CrudRepository

interface CardPaymentLogRepository : CrudRepository<CardPaymentLog, String>
