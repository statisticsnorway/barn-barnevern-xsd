package no.ssb.barn.validation.rule

import no.ssb.barn.report.ReportEntry
import no.ssb.barn.report.WarningLevel
import no.ssb.barn.validation.AbstractRule
import no.ssb.barn.validation.ValidationContext
import no.ssb.barn.xsd.PlanType

class PlanStartDateBeforeBusinessStartDate : AbstractRule(
    WarningLevel.ERROR,
    "Plan Kontroll 2e: Startdato mot individets startdato",
    PlanType::class.java.simpleName
) {
    override fun validate(context: ValidationContext): List<ReportEntry>? {
        val sak = context.rootObject.sak

        return sak.virksomhet.asSequence()
            .flatMap { virksomhet ->
                virksomhet.plan.asSequence()
                    .filter { plan ->
                        plan.startDato.isBefore(virksomhet.startDato)
                    }
                    .map {
                        createReportEntry(
                            "Planens startdato (${it.startDato}) er før"
                                    + " virksomhetens startdato (${virksomhet.startDato})",
                            it.id
                        )
                    }
            }
            .toList()
            .ifEmpty { null }
    }
}