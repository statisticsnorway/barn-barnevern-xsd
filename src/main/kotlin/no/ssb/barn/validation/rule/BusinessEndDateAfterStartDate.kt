package no.ssb.barn.validation.rule

import no.ssb.barn.report.ReportEntry
import no.ssb.barn.report.WarningLevel
import no.ssb.barn.validation.AbstractRule
import no.ssb.barn.validation.ValidationContext
import no.ssb.barn.xsd.VirksomhetType

class BusinessEndDateAfterStartDate : AbstractRule(
    WarningLevel.ERROR,
    "Virksomhet Kontroll 2a: Startdato er etter sluttdato",
    VirksomhetType::class.java.simpleName
) {
    override fun validate(context: ValidationContext): List<ReportEntry>? {
        return null
/*
        TODO fix me
        val startDate = context.rootObject.sak.virksomhet[0].startDato
        val endDate = context.rootObject.sak.virksomhet[0].sluttDato

        return if (endDate?.isBefore(startDate) != true)
            null
        else
            createSingleReportEntryList(
                "Virksomhetens startdato ($startDate) er etter sluttdato ($endDate)",
                context.rootObject.sak.id
            )
*/
    }
}