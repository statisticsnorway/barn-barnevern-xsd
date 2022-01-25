package no.ssb.barn.generator

import no.ssb.barn.xsd.BarnevernType
import no.ssb.barn.xsd.VirksomhetType
import java.time.LocalDate
import java.util.*

data class CaseEntry(
    val id: UUID,
    var barnevern: BarnevernType,
    var updated: LocalDate,
    var generation: Int = 1,
    var state: BarnevernState = BarnevernState.MESSAGE
) {
    val lastCompany: VirksomhetType
        get() = this.barnevern.sak.virksomhet.last()

    fun resetCompany() : VirksomhetType {
        this.barnevern.sak.virksomhet = mutableListOf(lastCompany.shallowClone())
        return lastCompany
    }
}

fun VirksomhetType.shallowClone() : VirksomhetType {
    return VirksomhetType(
        startDato = startDato,
        sluttDato = sluttDato,
        organisasjonsnummer = organisasjonsnummer,
        bydelsnummer = bydelsnummer,
        bydelsnavn = bydelsnavn,
        distriktsnummer = distriktsnummer
    )
}