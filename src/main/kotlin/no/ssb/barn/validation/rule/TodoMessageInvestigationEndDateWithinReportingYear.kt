package no.ssb.barn.validation.rule

import no.ssb.barn.framework.AbstractRule
import no.ssb.barn.framework.ValidationContext
import no.ssb.barn.report.ReportEntry
import no.ssb.barn.report.WarningLevel

class TodoMessageInvestigationEndDateWithinReportingYear : AbstractRule(
    WarningLevel.ERROR,
    "Undersøkelse Kontroll 2b: Sluttdato mot rapporteringsår",
    "TODO"
) {

    override fun validate(context: ValidationContext): List<ReportEntry>? {
        TODO("Not yet implemented")
    }
}