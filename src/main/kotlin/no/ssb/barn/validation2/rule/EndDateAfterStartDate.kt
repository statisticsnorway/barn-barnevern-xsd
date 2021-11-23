package no.ssb.barn.validation2.rule

import no.ssb.barn.framework.AbstractRule
import no.ssb.barn.framework.ValidationContext
import no.ssb.barn.report.ReportEntry
import no.ssb.barn.report.WarningLevel

class EndDateAfterStartDate : AbstractRule(
    WarningLevel.ERROR,
    "Individ Kontroll 02a: Startdato etter sluttdato"
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