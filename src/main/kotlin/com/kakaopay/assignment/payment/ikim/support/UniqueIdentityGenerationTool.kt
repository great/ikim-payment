package com.kakaopay.assignment.payment.ikim.support

import org.springframework.stereotype.Component
import java.util.UUID

@Component
object UniqueIdentityGenerationTool : IdGenerationTool {
    private const val LENGTH = 20

    override fun next() = UUID.randomUUID().toString().replace("-", "").substring(0, LENGTH)
}
