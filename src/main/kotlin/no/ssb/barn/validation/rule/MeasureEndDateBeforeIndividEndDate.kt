package no.ssb.barn.validation.rule

import no.ssb.barn.framework.AbstractRule
import no.ssb.barn.framework.ValidationContext
import no.ssb.barn.report.ReportEntry
import no.ssb.barn.report.WarningLevel
import no.ssb.barn.xsd.TiltakType

class MeasureEndDateBeforeIndividEndDate : AbstractRule(
    WarningLevel.ERROR,
    "Tiltak Kontroll 2c: Sluttdato mot individets sluttdato",
    TiltakType::class.java.simpleName
) {
    override fun validate(context: ValidationContext): List<ReportEntry>? {
        val sak = context.rootObject.sak
        val individEndDate = sak.sluttDato ?: return null

        return sak.virksomhet.asSequence()
            .mapNotNull { virksomhet -> virksomhet.tiltak }
            .flatten()
            .filter { tiltak ->
                tiltak.konklusjon?.sluttDato?.isAfter(individEndDate) == true
            }
            .map {
                createReportEntry(
                    "Tiltak (${it.id}). Sluttdato"
                            + " (${it.konklusjon?.sluttDato}) er etter individets"
                            + " sluttdato ($individEndDate)"
                )
            }
            .toList()
            .ifEmpty { null }
    }
}