package no.ssb.barn.validation.rule.investigation

import no.ssb.barn.report.ReportEntry
import no.ssb.barn.report.WarningLevel
import no.ssb.barn.validation.AbstractRule
import no.ssb.barn.validation.ValidationContext
import no.ssb.barn.xsd.UndersokelseType

class InvestigationStartDateBeforeCaseStartDate : AbstractRule(
    WarningLevel.ERROR,
    "Undersøkelse Kontroll 2e: StartDato er før virksomhetens StartDato",
    UndersokelseType::class.java.simpleName
) {
    override fun validate(context: ValidationContext): List<ReportEntry>? {
        val sak = context.rootObject.sak

        return sak.undersokelse.asSequence()
            .filter { undersokelse ->
                undersokelse.startDato.isBefore(sak.startDato)
            }
            .map {
                createReportEntry(
                    "Undersøkelsens startdato (${it.startDato}) "
                            + "er før sakens startdato (${sak.startDato})",
                    it.id
                )
            }
            .toList()
            .ifEmpty { null }
    }
}