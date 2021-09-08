package com.kakaopay.assignment.payment.ikim.component

import org.apache.commons.codec.binary.Base64
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class Aes256EncryptionToolTest {
    private val sut = Aes256EncryptionTool()

    @Test
    fun base64enc() {
        val claimed = "MTIzNDU2Nzg5MEFCQ0RFRg=="
        assertThat(
            Base64.encodeBase64String("1234567890ABCDEF".toByteArray())
        ).isEqualTo(claimed)
    }

    @Test
    fun enc() {
        assertThat(sut.encrypt("test_string"))
            .isEqualTo("V1nV0tNbldCqsvIzJ9kPmg==")
    }

    @Test
    fun dec() {
        assertThat(sut.decrypt("V1nV0tNbldCqsvIzJ9kPmg=="))
            .isEqualTo("test_string")
    }
}
