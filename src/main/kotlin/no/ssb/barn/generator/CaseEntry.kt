package no.ssb.barn.generator

import no.ssb.barn.xsd.BarnevernType
import java.time.LocalDate
import java.util.*

data class CaseEntry(
    val id: UUID,
    var barnevern: BarnevernType,
    var generation: Int = 1,
    var updated: LocalDate = LocalDate.now()
)
