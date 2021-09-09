package kim.intae.assignment.payment.duel

import kim.intae.assignment.payment.component.IdGenerationTool

class DummyIdGenerationTool(private val keys: List<String>): IdGenerationTool {
    private var idx = 0

    override fun next(): String {
        return keys[idx++]
    }
}
