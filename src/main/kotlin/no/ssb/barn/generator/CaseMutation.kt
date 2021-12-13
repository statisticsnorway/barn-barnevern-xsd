package no.ssb.barn.generator

import no.ssb.barn.xsd.BarnevernType
import java.time.LocalDate

data class CaseMutation(
    val generation: Int = 1,
    val isMutable: Boolean = true,
    val created: LocalDate = LocalDate.now(),
    val barnevern: BarnevernType
)
