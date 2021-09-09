package kim.intae.assignment.payment.component

interface EncryptionTool {
    fun encrypt(payload: String): String
    fun decrypt(token: String): String
}
