package no.ssb.barn.generator

import no.ssb.barn.xsd.BarnevernType
import java.time.LocalDateTime
import java.util.*

data class CaseEntry(
    val id: UUID,
    var barnevern: BarnevernType,
    var updated: LocalDateTime,
    var generation: Int = 1,
    var state: BarnevernState = BarnevernState.MESSAGE
)
