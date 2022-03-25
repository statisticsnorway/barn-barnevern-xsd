package no.ssb.barn.validation.rule.decision

import no.ssb.barn.report.ReportEntry
import no.ssb.barn.report.WarningLevel
import no.ssb.barn.validation.AbstractRule
import no.ssb.barn.validation.ValidationContext
import no.ssb.barn.xsd.VedtakType

class DecisionEndDateAfterCaseEndDate : AbstractRule(
    WarningLevel.ERROR,
    "Vedtak Kontroll 2c: SluttDato mot sakens SluttDato",
    VedtakType::class.java.simpleName
) {
    override fun validate(context: ValidationContext): List<ReportEntry>? {
        val case = context.rootObject.sak

        return if (case.sluttDato == null) {
            null
        } else {
            context.rootObject.sak.vedtak.asSequence()
                .filter { decision ->
                    val conclusion = decision.konklusjon // when JaCoCo improves, use "?."

                    conclusion != null
                            && conclusion.sluttDato!!.isAfter(case.sluttDato)
                }
                .map {
                    createReportEntry(
                        "Vedtakets sluttdato (${it.konklusjon!!.sluttDato}) er etter sakens sluttdato (${case.sluttDato})",
                        it.id!!
                    )
                }
                .toList()
                .ifEmpty { null }
        }
    }
}