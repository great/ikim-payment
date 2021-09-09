package kim.intae.assignment.payment.repository

import kim.intae.assignment.payment.domain.entity.CardRefundLog
import org.springframework.data.repository.CrudRepository

interface CardRefundLogRepository : CrudRepository<CardRefundLog, String> {
    fun findByPaidUniqueId(id: String): CardRefundLog?
}
