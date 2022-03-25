package no.ssb.barn.validation.rule.decision

import no.ssb.barn.report.ReportEntry
import no.ssb.barn.report.WarningLevel
import no.ssb.barn.validation.AbstractRule
import no.ssb.barn.validation.ValidationContext
import no.ssb.barn.xsd.OversendelsePrivatKravType

class DecisionRequirementStartDateAfterEndDate : AbstractRule(
    WarningLevel.ERROR,
    "Vedtak Kontroll 2f: Krav sin StartDato er etter SluttDato",
    OversendelsePrivatKravType::class.java.simpleName
) {
    override fun validate(context: ValidationContext): List<ReportEntry>? =
        context.rootObject.sak.vedtak.asSequence()
            .flatMap { it.krav }
            .filter { requirement ->
                val conclusion = requirement.konklusjon // when JaCoCo improves, use "?."
                conclusion != null
                        && requirement.startDato!!.isAfter(conclusion.sluttDato)
            }
            .map { requirement ->
                createReportEntry(
                    "Kravets startdato (${requirement.startDato}) er etter sluttdato (${requirement.konklusjon!!.sluttDato})",
                    requirement.id!!
                )
            }
            .toList()
            .ifEmpty { null }
}