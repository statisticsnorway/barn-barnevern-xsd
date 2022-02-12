package no.ssb.barn.validation.rule

import no.ssb.barn.report.ReportEntry
import no.ssb.barn.report.WarningLevel
import no.ssb.barn.validation.AbstractRule
import no.ssb.barn.validation.ValidationContext
import no.ssb.barn.xsd.UndersokelseType

class InvestigationStartDateBeforeBusinessStartDate : AbstractRule(
    WarningLevel.ERROR,
    "Undersøkelse Kontroll 2e: StartDato er før virksomhetens StartDato",
    UndersokelseType::class.java.simpleName
) {
    override fun validate(context: ValidationContext): List<ReportEntry>? {
        val sak = context.rootObject.sak

        return sak.virksomhet.asSequence()
            .map { virksomhet ->
                virksomhet.undersokelse.asSequence()
                    .filter { undersokelse ->
                        undersokelse.startDato.isBefore(virksomhet.startDato)
                    }
                    .map {
                        createReportEntry(
                            "Undersøkelsens startdato (${it.startDato}) "
                                    + "er før virksomhetens startdato (${virksomhet.startDato})",
                            it.id
                        )
                    }
            }
            .flatten()
            .toList()
            .ifEmpty { null }
    }
}