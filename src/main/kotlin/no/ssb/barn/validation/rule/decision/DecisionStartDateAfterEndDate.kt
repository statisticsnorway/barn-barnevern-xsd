package no.ssb.barn.validation.rule.decision

import no.ssb.barn.report.ReportEntry
import no.ssb.barn.report.WarningLevel
import no.ssb.barn.validation.AbstractRule
import no.ssb.barn.validation.ValidationContext
import no.ssb.barn.xsd.VedtakType

class DecisionStartDateAfterEndDate : AbstractRule(
    WarningLevel.ERROR,
    "Vedtak Kontroll 2a: StartDato er etter SluttDato",
    VedtakType::class.java.simpleName
) {
    override fun validate(context: ValidationContext): List<ReportEntry>? =
        context.rootObject.sak.vedtak.asSequence()
            .filter { decision ->
                val conclusion = decision.konklusjon // when JaCoCo improves, use "?."
                conclusion != null && decision.startDato.isAfter(conclusion.sluttDato)
            }
            .map {
                createReportEntry(
                    "Vedtakets startdato (${it.startDato}) er etter sluttdato (${it.konklusjon!!.sluttDato})",
                    it.id
                )
            }
            .toList()
            .ifEmpty { null }
}