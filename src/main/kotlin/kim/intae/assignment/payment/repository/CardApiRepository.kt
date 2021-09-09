package kim.intae.assignment.payment.repository

import kim.intae.assignment.payment.domain.entity.CardPaymentApi
import org.springframework.data.repository.CrudRepository

interface CardApiRepository : CrudRepository<CardPaymentApi, String>
