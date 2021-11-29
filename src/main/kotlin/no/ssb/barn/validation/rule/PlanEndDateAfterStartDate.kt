package no.ssb.barn.validation.rule

import no.ssb.barn.framework.AbstractRule
import no.ssb.barn.framework.ValidationContext
import no.ssb.barn.report.ReportEntry
import no.ssb.barn.report.WarningLevel
import no.ssb.barn.xsd.PlanType

class PlanEndDateAfterStartDate : AbstractRule(
    WarningLevel.ERROR,
    "Plan Kontroll 2a: Startdato etter sluttdato",
    PlanType::class.java.simpleName
) {
    override fun validate(context: ValidationContext): List<ReportEntry>? {
        return context.rootObject.sak.virksomhet.asSequence()
            .mapNotNull { virksomhet -> virksomhet.plan }
            .flatten()
            .filter { plan ->
                plan.startDato > plan.konklusjon?.sluttDato
            }
            .map {
                createReportEntry(
                    "Plan (${it.id}}). Planens startdato (${it.startDato})"
                            + " er etter planens sluttdato"
                            + " (${it.konklusjon?.sluttDato})",
                    it.id
                )
            }
            .toList()
            .ifEmpty { null }
    }
}