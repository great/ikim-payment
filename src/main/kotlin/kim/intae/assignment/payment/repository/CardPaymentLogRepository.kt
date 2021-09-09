package kim.intae.assignment.payment.repository

import kim.intae.assignment.payment.domain.entity.CardPaymentLog
import org.springframework.data.repository.CrudRepository

interface CardPaymentLogRepository : CrudRepository<CardPaymentLog, String>
