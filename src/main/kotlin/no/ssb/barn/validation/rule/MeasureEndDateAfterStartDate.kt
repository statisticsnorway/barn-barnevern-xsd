package no.ssb.barn.validation.rule

import no.ssb.barn.framework.AbstractRule
import no.ssb.barn.framework.ValidationContext
import no.ssb.barn.report.ReportEntry
import no.ssb.barn.report.WarningLevel
import no.ssb.barn.xsd.TiltakType

class MeasureEndDateAfterStartDate : AbstractRule(
    WarningLevel.ERROR,
    "Tiltak Kontroll 2a: Startdato etter sluttdato",
    TiltakType::class.java.simpleName
) {
    override fun validate(context: ValidationContext): List<ReportEntry>? =
        context.rootObject.sak.virksomhet.asSequence()
            .mapNotNull { virksomhet -> virksomhet.tiltak }
            .flatten()
            .filter { tiltak ->
                tiltak.konklusjon?.sluttDato?.isBefore(tiltak.startDato) == true
            }
            .map {
                createReportEntry(
                    "Tiltak (${it.id}}). Startdato (${it.startDato})"
                            + " for tiltaket er etter sluttdato"
                            + " (${it.konklusjon?.sluttDato}) for tiltaket"
                )
            }
            .toList()
            .ifEmpty { null }
}