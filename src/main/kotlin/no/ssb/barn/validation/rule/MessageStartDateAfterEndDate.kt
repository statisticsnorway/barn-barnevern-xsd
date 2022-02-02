package no.ssb.barn.validation.rule

import no.ssb.barn.validation.AbstractRule
import no.ssb.barn.validation.ValidationContext
import no.ssb.barn.report.ReportEntry
import no.ssb.barn.report.WarningLevel
import no.ssb.barn.xsd.MeldingType

class MessageStartDateAfterEndDate : AbstractRule(
    WarningLevel.ERROR,
    "Melding Kontroll 2a: Startdato etter sluttdato",
    MeldingType::class.java.simpleName
) {
    override fun validate(context: ValidationContext): List<ReportEntry>? =
        context.rootObject.sak.virksomhet.asSequence()
            .flatMap { virksomhet -> virksomhet.melding }
            .filter { melding ->
                with(melding) {
                    konklusjon != null && startDato.isAfter(konklusjon!!.sluttDato)
                }
            }
            .map {
                createReportEntry(
                    "Meldingens startdato (${it.startDato}) er etter"
                            + " meldingens sluttdato (${it.konklusjon!!.sluttDato})",
                    it.id
                )
            }
            .toList()
            .ifEmpty { null }
}