package com.kakaopay.assignment.payment.ikim.component

interface EncryptionTool {
    fun encrypt(payload: String): String
    fun decrypt(token: String): String
}
