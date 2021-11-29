package no.ssb.barn.validation.rule

import no.ssb.barn.framework.AbstractRule
import no.ssb.barn.framework.ValidationContext
import no.ssb.barn.report.ReportEntry
import no.ssb.barn.report.WarningLevel
import no.ssb.barn.xsd.PlanType

class PlanStartDateAfterIndividStartDate : AbstractRule(
    WarningLevel.ERROR,
    "Plan Kontroll 2e: Startdato mot individets startdato",
    PlanType::class.java.simpleName
) {
    override fun validate(context: ValidationContext): List<ReportEntry>? {
        val individStartDate = context.rootObject.sak.startDato

        return context.rootObject.sak.virksomhet.asSequence()
            .mapNotNull { virksomhet -> virksomhet.plan }
            .flatten()
            .filter { plan ->
                plan.startDato < individStartDate
            }
            .map {
                createReportEntry(
                    """Plan (${it.id}}). Startdato (${it.startDato}) skal 
                            |være lik eller etter individets startdato 
                            |($individStartDate)"""
                        .trimMargin()
                        .replace("\n", "")
                )
            }
            .toList()
            .ifEmpty { null }
    }
}