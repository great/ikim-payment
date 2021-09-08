package com.kakaopay.assignment.payment.ikim.support

interface EncryptionTool {
    fun encrypt(payload: String): String
    fun decrypt(token: String): String
}
