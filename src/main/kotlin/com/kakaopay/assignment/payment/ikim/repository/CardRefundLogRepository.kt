package com.kakaopay.assignment.payment.ikim.repository

import com.kakaopay.assignment.payment.ikim.domain.entity.CardRefundLog
import org.springframework.data.repository.CrudRepository

interface CardRefundLogRepository : CrudRepository<CardRefundLog, String> {
    fun findByPaidUniqueId(id: String): CardRefundLog?
}
