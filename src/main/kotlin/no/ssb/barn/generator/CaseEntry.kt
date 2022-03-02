package no.ssb.barn.generator

import no.ssb.barn.xsd.BarnevernType
import java.time.ZonedDateTime
import java.util.*

data class CaseEntry(
    val id: UUID,
    var barnevern: BarnevernType,
    var updated: ZonedDateTime,
    var generation: Int = 1,
    var state: BarnevernState = BarnevernState.MESSAGE
)
