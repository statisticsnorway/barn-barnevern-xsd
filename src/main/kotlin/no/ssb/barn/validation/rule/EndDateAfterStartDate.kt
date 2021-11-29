package no.ssb.barn.validation.rule

import no.ssb.barn.framework.AbstractRule
import no.ssb.barn.framework.ValidationContext
import no.ssb.barn.report.ReportEntry
import no.ssb.barn.report.WarningLevel
import no.ssb.barn.util.ValidationUtils.Companion.secondDateIsAfterFirstDate
import no.ssb.barn.xsd.SakType

class EndDateAfterStartDate : AbstractRule(
    WarningLevel.ERROR,
    "Individ Kontroll 02a: Startdato etter sluttdato",
    SakType::class.java.simpleName
) {
    override fun validate(context: ValidationContext): List<ReportEntry>? {
        val startDate = context.rootObject.sak.startDato
        val endDate = context.rootObject.sak.sluttDato

        return if (secondDateIsAfterFirstDate(startDate, endDate))
            null
        else
            createSingleReportEntryList(
                "Individets startdato ($startDate) er etter sluttdato ($endDate)",
                context.rootObject.sak.id
            )
    }
}