package no.ssb.barn.validation.rule.plan

import no.ssb.barn.report.ReportEntry
import no.ssb.barn.report.WarningLevel
import no.ssb.barn.validation.AbstractRule
import no.ssb.barn.validation.ValidationContext
import no.ssb.barn.xsd.PlanType

class PlanEvaluationDateAfterCaseEndDate : AbstractRule(
    WarningLevel.ERROR,
    "Plan Kontroll 2f: UtfortDato er etter sakens SluttDato",
    PlanType::class.java.simpleName
) {
    override fun validate(context: ValidationContext): List<ReportEntry>? {
        val sak = context.rootObject.sak

        return if (sak.sluttDato == null) {
            null
        } else {
            sak.plan.asSequence()
                .flatMap { plan ->
                    plan.evaluering
                        .filter { evaluation ->
                            evaluation.utfortDato.isAfter(sak.sluttDato)
                        }
                        .map {
                            createReportEntry(
                                "Utført evaluering (${it.utfortDato}) er etter sluttdato (${sak.sluttDato})",
                                plan.id
                            )
                        }
                }
                .toList()
                .ifEmpty { null }
        }
    }
}