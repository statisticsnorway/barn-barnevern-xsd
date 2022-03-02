package no.ssb.barn.validation.rule

import no.ssb.barn.report.ReportEntry
import no.ssb.barn.report.WarningLevel
import no.ssb.barn.validation.AbstractRule
import no.ssb.barn.validation.ValidationContext
import no.ssb.barn.xsd.TiltakType

class MeasureStartDateAfterIndividStartDate : AbstractRule(
    WarningLevel.ERROR,
    "Tiltak Kontroll 2e: Startdato mot individets startdato",
    TiltakType::class.java.simpleName
) {
    override fun validate(context: ValidationContext): List<ReportEntry>? {
        val individStartDate = context.rootObject.sak.startDato

        return context.rootObject.sak.tiltak.asSequence()
            .filter { tiltak ->
                tiltak.startDato.isBefore(individStartDate)
            }
            .map {
                createReportEntry(
                    "Tiltak (${it.id}}). Startdato (${it.startDato}) skal"
                            + " være lik eller etter individets startdato"
                            + " ($individStartDate)",
                    it.id
                )
            }
            .toList()
            .ifEmpty { null }
    }
}