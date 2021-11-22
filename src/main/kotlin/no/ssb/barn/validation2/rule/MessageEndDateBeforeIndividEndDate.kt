package no.ssb.barn.validation2.rule

import no.ssb.barn.framework.AbstractRule
import no.ssb.barn.framework.ValidationContext
import no.ssb.barn.report.ReportEntry
import no.ssb.barn.report.WarningLevel

class MessageEndDateBeforeIndividEndDate : AbstractRule(
    WarningLevel.ERROR,
    "Melding Kontroll 2c: Sluttdato mot individets sluttdato"
) {

    override fun validate(context: ValidationContext): List<ReportEntry>? {
        TODO("Not yet implemented")

/*
        "Individets startdato  (" + individStartDatoString
        + ") er etter sluttdato (" + individSluttDatoString
        + ")", Constants.CRITICAL_ERROR), individStartDato,
*/

    }
}