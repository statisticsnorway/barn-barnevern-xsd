package no.ssb.barn.validation.rule

import no.ssb.barn.framework.AbstractRule
import no.ssb.barn.framework.ValidationContext
import no.ssb.barn.report.ReportEntry
import no.ssb.barn.report.WarningLevel

class JonOleTodoEndDateAfterCountTimeStamp : AbstractRule(
    WarningLevel.ERROR,
    "Individ Kontroll 02b: Sluttdato mot versjon"
) {

    override fun validate(context: ValidationContext): List<ReportEntry>? {
        TODO("Not yet implemented")

/*
        "Individets sluttdato (" + individSluttDatoString
        + ") er før forrige telletidspunkt ("
        + forrigeTelleDatoString + ")",
        Constants.CRITICAL_ERROR), forrigeTelle
*/
    }
}