package com.kakaopay.assignment.payment.ikim.duel

import com.kakaopay.assignment.payment.ikim.component.IdGenerationTool

class DummyIdGenerationTool(private val keys: List<String>): IdGenerationTool {
    private var idx = 0

    override fun next(): String {
        return keys[idx++]
    }
}
