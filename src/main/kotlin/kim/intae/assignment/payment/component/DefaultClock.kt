package kim.intae.assignment.payment.component

import org.springframework.stereotype.Component
import java.time.Clock
import java.time.ZoneId

@Component
class DefaultClock : Clock() {
    private val clock = systemUTC()

    override fun getZone() = clock.zone
    override fun withZone(zone: ZoneId?) = clock.withZone(zone)
    override fun instant() = clock.instant()
}
