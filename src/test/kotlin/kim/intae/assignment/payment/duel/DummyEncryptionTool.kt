package kim.intae.assignment.payment.duel

import kim.intae.assignment.payment.component.EncryptionTool

class DummyEncryptionTool(private val cardBundle: String, private val fakeEncryption: String) : EncryptionTool {
    override fun encrypt(payload: String): String {
        require(payload == cardBundle) { "fake tool only accepts predefined cardNo|expiry|cvc value" }
        return fakeEncryption
    }

    override fun decrypt(token: String): String {
        require(token == fakeEncryption) { "fake tool only accepts predefined fake encryption value" }
        return cardBundle
    }
}
