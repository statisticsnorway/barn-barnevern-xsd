package no.ssb.barn.validation.rule.investigation

import no.ssb.barn.report.ReportEntry
import no.ssb.barn.report.WarningLevel
import no.ssb.barn.validation.AbstractRule
import no.ssb.barn.validation.ValidationContext
import no.ssb.barn.xsd.UndersokelseType

class InvestigationEndDateAfterCaseEndDate : AbstractRule(
    WarningLevel.ERROR,
    "Undersøkelse Kontroll 2c: Sluttdato er etter virksomhetens sluttdato",
    UndersokelseType::class.java.simpleName
) {
    override fun validate(context: ValidationContext): List<ReportEntry>? {
        val sak = context.rootObject.sak

        return if (sak.sluttDato == null) {
            null
        } else {
            sak.undersokelse.asSequence()
                .filter { undersokelse ->
                    val conclusion = undersokelse.konklusjon
                    conclusion != null && undersokelse.konklusjon!!.sluttDato!!.isAfter(sak.sluttDato)
                }
                .map {
                    createReportEntry(
                        "Undersøkelsens sluttdato (${it.konklusjon!!.sluttDato})"
                                + " er etter sakens sluttdato (${sak.sluttDato})",
                        it.id!!
                    )
                }
                .toList()
                .ifEmpty { null }
        }
    }
}