package com.kakaopay.assignment.payment.ikim.component

import java.time.Clock
import java.time.Instant
import java.time.ZoneId

class FrozenClock : Clock() {
    private var zone = ZoneId.systemDefault()
    private var currentTimeMillis = systemDefaultZone().millis()

    override fun getZone(): ZoneId {
        return zone
    }

    override fun withZone(zone: ZoneId): Clock {
        val clock = FrozenClock()
        clock.zone = zone
        clock.currentTimeMillis = currentTimeMillis
        return clock
    }

    override fun instant(): Instant {
        return Instant.ofEpochMilli(currentTimeMillis)
    }
}
