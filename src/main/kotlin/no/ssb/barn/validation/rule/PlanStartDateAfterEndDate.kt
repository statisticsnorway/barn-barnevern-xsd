package no.ssb.barn.validation.rule

import no.ssb.barn.validation.AbstractRule
import no.ssb.barn.validation.ValidationContext
import no.ssb.barn.report.ReportEntry
import no.ssb.barn.report.WarningLevel
import no.ssb.barn.xsd.PlanType

class PlanStartDateAfterEndDate : AbstractRule(
    WarningLevel.ERROR,
    "Plan Kontroll 2a: Startdato etter sluttdato",
    PlanType::class.java.simpleName
) {
    override fun validate(context: ValidationContext): List<ReportEntry>? =
        context.rootObject.sak.virksomhet.asSequence()
            .flatMap { virksomhet -> virksomhet.plan }
            .filter { plan ->
                val conclusion = plan.konklusjon // when JaCoCo improves, use "?."
                conclusion != null
                        && plan.startDato.isAfter(conclusion.sluttDato)
            }
            .map {
                createReportEntry(
                    "Plan (${it.id}}). Planens startdato (${it.startDato})"
                            + " er etter planens sluttdato"
                            + " (${it.konklusjon!!.sluttDato})",
                    it.id
                )
            }
            .toList()
            .ifEmpty { null }
}