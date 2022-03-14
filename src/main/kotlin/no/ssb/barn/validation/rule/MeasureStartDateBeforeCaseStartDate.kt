package no.ssb.barn.validation.rule

import no.ssb.barn.report.ReportEntry
import no.ssb.barn.report.WarningLevel
import no.ssb.barn.validation.AbstractRule
import no.ssb.barn.validation.ValidationContext
import no.ssb.barn.xsd.MeldingType
import java.util.*

class MeasureStartDateBeforeCaseStartDate : AbstractRule(
    WarningLevel.ERROR,
    "Tiltak Kontroll 2e: StartDato er før sakens StartDato",
    MeldingType::class.java.simpleName
) {
    override fun validate(context: ValidationContext): List<ReportEntry>? {
        val sak = context.rootObject.sak

        return sak.tiltak.asSequence()
            .filter { tiltak ->
                tiltak.startDato.isBefore(sak.startDato)
            }
            .map {
                createReportEntry(
                    "Tiltakets startdato (${it.startDato}) er før"
                            + " sakens startdato (${sak.startDato})",
                    it.id as UUID
                )
            }
            .toList()
            .ifEmpty { null }
    }
}