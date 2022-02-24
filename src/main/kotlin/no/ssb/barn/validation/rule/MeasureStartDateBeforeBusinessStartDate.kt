package no.ssb.barn.validation.rule

import no.ssb.barn.report.ReportEntry
import no.ssb.barn.report.WarningLevel
import no.ssb.barn.validation.AbstractRule
import no.ssb.barn.validation.ValidationContext
import no.ssb.barn.xsd.MeldingType

class MeasureStartDateBeforeBusinessStartDate : AbstractRule(
    WarningLevel.ERROR,
    "Tiltak Kontroll 2e: StartDato er før virksomhetens StartDato",
    MeldingType::class.java.simpleName
) {
    override fun validate(context: ValidationContext): List<ReportEntry>? {
        val sak = context.rootObject.sak

        return sak.virksomhet.asSequence()
            .flatMap { virksomhet ->
                virksomhet.tiltak.asSequence()
                    .filter { tiltak ->
                        tiltak.startDato.isBefore(virksomhet.startDato)
                    }
                    .map {
                        createReportEntry(
                            "Tiltakets startdato (${it.startDato}) er før"
                                    + " virksomhetens startdato (${virksomhet.startDato})",
                            it.id
                        )
                    }
            }
            .toList()
            .ifEmpty { null }
    }
}