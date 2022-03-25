package no.ssb.barn.validation.rule.decision

import no.ssb.barn.report.ReportEntry
import no.ssb.barn.report.WarningLevel
import no.ssb.barn.validation.AbstractRule
import no.ssb.barn.validation.ValidationContext
import no.ssb.barn.xsd.VedtakType

class DecisionStartDateBeforeCaseStartDate : AbstractRule(
    WarningLevel.ERROR,
    "Vedtak Kontroll 2e: StartDato er før sakens StartDato",
    VedtakType::class.java.simpleName
) {
    override fun validate(context: ValidationContext): List<ReportEntry>? =
        context.rootObject.sak.vedtak.asSequence()
            .filter { decision ->
                decision.startDato!!.isBefore(context.rootObject.sak.startDato)
            }
            .map {
                createReportEntry(
                    "Vedtakets startdato (${it.startDato}) er før sakens startdato (${context.rootObject.sak.startDato})",
                    it.id!!
                )
            }
            .toList()
            .ifEmpty { null }
}