package com.kakaopay.assignment.payment.ikim.support

import org.apache.commons.codec.binary.Base64
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

class Aes256EncryptionTool(iv: String, key: String) {
    private val iv: IvParameterSpec = IvParameterSpec(iv.toByteArray())
    private val encKey = encryptor(key)
    private val decKey = decryptor(key)

    fun encrypt(payload: String) = payload.toByteArray()
        .run { encKey.doFinal(this) }
        .run { Base64.encodeBase64String(this) }

    fun decrypt(token: String) = Base64.decodeBase64(token)
        .run { decKey.doFinal(this) }
        .run { String(this) }

    private fun encryptor(key: String) = Cipher.getInstance("AES/CBC/PKCS5Padding")
        .also { it.init(Cipher.ENCRYPT_MODE, key.toAesSecret(), this.iv) }

    private fun decryptor(key: String) = Cipher.getInstance("AES/CBC/PKCS5Padding")
        .also { it.init(Cipher.DECRYPT_MODE, key.toAesSecret(), this.iv) }

    private fun String.toAesSecret() = SecretKeySpec(this.toByteArray(), "AES")
}