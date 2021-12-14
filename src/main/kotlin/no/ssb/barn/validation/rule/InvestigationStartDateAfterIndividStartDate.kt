package no.ssb.barn.validation.rule

import no.ssb.barn.framework.AbstractRule
import no.ssb.barn.framework.ValidationContext
import no.ssb.barn.report.ReportEntry
import no.ssb.barn.report.WarningLevel
import no.ssb.barn.xsd.UndersokelseType

class InvestigationStartDateAfterIndividStartDate : AbstractRule(
    WarningLevel.ERROR,
    "Undersøkelse Kontroll 2e: Startdato mot individets startdato",
    UndersokelseType::class.java.simpleName
) {
    override fun validate(context: ValidationContext): List<ReportEntry>? {
        val individStartDate = context.rootObject.sak.startDato

        return context.rootObject.sak.virksomhet.asSequence()
            .flatMap { virksomhet -> virksomhet.undersokelse }
            .filter { undersokelse ->
                undersokelse.startDato.isBefore(individStartDate)
            }
            .map {
                createReportEntry(
                    "Undersøkelse (${it.id}}). Startdato (${it.startDato})"
                            + " skal være lik eller etter individets startdato"
                            + " ($individStartDate)",
                    it.id
                )
            }
            .toList()
            .ifEmpty { null }
    }
}