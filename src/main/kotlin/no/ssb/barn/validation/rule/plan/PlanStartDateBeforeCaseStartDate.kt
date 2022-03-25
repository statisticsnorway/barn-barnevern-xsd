package no.ssb.barn.validation.rule.plan

import no.ssb.barn.report.ReportEntry
import no.ssb.barn.report.WarningLevel
import no.ssb.barn.validation.AbstractRule
import no.ssb.barn.validation.ValidationContext
import no.ssb.barn.xsd.PlanType

class PlanStartDateBeforeCaseStartDate : AbstractRule(
    WarningLevel.ERROR,
    "Plan Kontroll 2e: Startdato mot sakens startdato",
    PlanType::class.java.simpleName
) {
    override fun validate(context: ValidationContext): List<ReportEntry>? {
        val sak = context.rootObject.sak

        return sak.plan.asSequence()
            .filter { plan ->
                plan.startDato!!.isBefore(sak.startDato)
            }
            .map {
                createReportEntry(
                    "Planens startdato (${it.startDato}) er før"
                            + " sakens startdato (${sak.startDato})",
                    it.id!!
                )
            }
            .toList()
            .ifEmpty { null }
    }
}