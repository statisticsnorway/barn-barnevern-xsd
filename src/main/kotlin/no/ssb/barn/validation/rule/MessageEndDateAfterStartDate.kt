package no.ssb.barn.validation.rule

import no.ssb.barn.framework.AbstractRule
import no.ssb.barn.framework.ValidationContext
import no.ssb.barn.report.ReportEntry
import no.ssb.barn.report.WarningLevel
import no.ssb.barn.xsd.MeldingType

class MessageEndDateAfterStartDate : AbstractRule(
    WarningLevel.ERROR,
    "Melding Kontroll 2a: Startdato etter sluttdato",
    MeldingType::class.java.simpleName
) {
    override fun validate(context: ValidationContext): List<ReportEntry>? =
        context.rootObject.sak.virksomhet.asSequence()
            .mapNotNull { virksomhet -> virksomhet.melding }
            .flatten()
            .filter { melding ->
                val conclusion = melding.konklusjon
                conclusion != null && melding.startDato > conclusion.sluttDato
            }
            .map {
                createReportEntry(
                    "Meldingens startdato (${it.startDato}) er etter "
                            + "meldingens sluttdato (${it.konklusjon?.sluttDato})",
                    it.id
                )
            }
            .toList()
            .ifEmpty { null }
}