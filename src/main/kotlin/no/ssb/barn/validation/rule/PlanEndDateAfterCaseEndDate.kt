package no.ssb.barn.validation.rule

import no.ssb.barn.report.ReportEntry
import no.ssb.barn.report.WarningLevel
import no.ssb.barn.validation.AbstractRule
import no.ssb.barn.validation.ValidationContext
import no.ssb.barn.xsd.PlanType
import java.util.*

class PlanEndDateAfterCaseEndDate : AbstractRule(
    WarningLevel.ERROR,
    "Plan Kontroll 2c: Sluttdato er etter sakens sluttdato",
    PlanType::class.java.simpleName
) {
    override fun validate(context: ValidationContext): List<ReportEntry>? {
        val sak = context.rootObject.sak

        return if (sak.sluttDato == null) {
            null
        } else {
            sak.plan.asSequence()
                .filter { plan ->
                    val conclusion = plan.konklusjon
                    conclusion != null && conclusion.sluttDato!!.isAfter(sak.sluttDato)
                }
                .map {
                    createReportEntry(
                        "Planens sluttdato (${it.konklusjon!!.sluttDato})"
                                + " er etter sakens"
                                + " sluttdato (${sak.sluttDato})",
                        it.id as UUID
                    )
                }
                .toList()
                .ifEmpty { null }
        }
    }
}