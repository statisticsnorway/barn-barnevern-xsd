package no.ssb.barn.validation.rule

import no.ssb.barn.validation.AbstractRule
import no.ssb.barn.validation.ValidationContext
import no.ssb.barn.report.ReportEntry
import no.ssb.barn.report.WarningLevel
import no.ssb.barn.xsd.PlanType

class PlanEndDateAfterBusinessEndDate : AbstractRule(
    WarningLevel.ERROR,
    "Plan Kontroll 2c: Sluttdato er etter virksomhetens sluttdato",
    PlanType::class.java.simpleName
) {
    override fun validate(context: ValidationContext): List<ReportEntry>? {
        val sak = context.rootObject.sak

        return sak.virksomhet.asSequence()
            .map { virksomhet ->
                val businessEndDate = virksomhet.sluttDato ?: return@map null

                virksomhet.plan.asSequence()
                    .filter { plan ->
                        val conclusion = plan.konklusjon
                        conclusion != null && conclusion.sluttDato.isAfter(businessEndDate)
                    }
                    .map {
                        createReportEntry(
                            "Planens sluttdato (${it.konklusjon!!.sluttDato})"
                                    + " er etter virksomhetens"
                                    + " sluttdato ($businessEndDate)",
                            it.id
                        )
                    }
            }
            .filterNotNull()
            .flatten()
            .toList()
            .ifEmpty { null }

//        val sak = context.rootObject.sak
//        val individEndDate = sak.sluttDato ?: return null
//
//        return sak.virksomhet.asSequence()
//            .flatMap { virksomhet -> virksomhet.plan }
//            .filter { plan ->
//                val conclusion = plan.konklusjon // when JaCoCo improves, use "?."
//                conclusion != null
//                        && conclusion.sluttDato.isAfter(individEndDate)
//            }
//            .map {
//                createReportEntry(
//                    "Plan (${it.id}). Planens sluttdato"
//                            + " (${it.konklusjon!!.sluttDato}) er etter individets"
//                            + " sluttdato ($individEndDate)",
//                    it.id
//                )
//            }
//            .toList()
//            .ifEmpty { null }
    }
}