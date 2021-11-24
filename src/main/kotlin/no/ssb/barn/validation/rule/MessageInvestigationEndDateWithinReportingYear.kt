package no.ssb.barn.validation.rule

import no.ssb.barn.framework.AbstractRule
import no.ssb.barn.framework.ValidationContext
import no.ssb.barn.report.ReportEntry
import no.ssb.barn.report.WarningLevel

class MessageInvestigationEndDateWithinReportingYear : AbstractRule(
    WarningLevel.ERROR,
    "Undersøkelse Kontroll 2b: Sluttdato mot rapporteringsår"
) {

    override fun validate(context: ValidationContext): List<ReportEntry>? {
        TODO("Not yet implemented")
    }
}