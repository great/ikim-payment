package com.kakaopay.assignment.payment.ikim.repository

import com.kakaopay.assignment.payment.ikim.domain.entity.CardPaymentApi
import org.springframework.data.repository.CrudRepository

interface CardApiRepository : CrudRepository<CardPaymentApi, String>
