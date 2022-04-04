package no.ssb.barn.validation.rule.plan

import no.ssb.barn.report.ReportEntry
import no.ssb.barn.report.WarningLevel
import no.ssb.barn.validation.AbstractRule
import no.ssb.barn.validation.ValidationContext
import no.ssb.barn.xsd.PlanType

class PlanEvaluationDateBeforeCaseStartDate : AbstractRule(
    WarningLevel.ERROR,
    "Plan Kontroll 2g: UtfortDato er før sakens StartDato",
    PlanType::class.java.simpleName
) {
    override fun validate(context: ValidationContext): List<ReportEntry>? {
        val sak = context.rootObject.sak

        return sak.plan.asSequence()
            .flatMap { plan ->
                plan.evaluering
                    .filter { evaluation ->
                        evaluation.utfortDato.isBefore(sak.startDato)
                    }
                    .map {
                        createReportEntry(
                            "Utført evaluering (${it.utfortDato}) er før startdato (${sak.startDato})",
                            plan.id
                        )
                    }
            }
            .toList()
            .ifEmpty { null }
    }
}