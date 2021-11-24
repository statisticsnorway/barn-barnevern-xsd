package no.ssb.barn.validation.rule

import no.ssb.barn.framework.AbstractRule
import no.ssb.barn.framework.ValidationContext
import no.ssb.barn.report.ReportEntry
import no.ssb.barn.report.WarningLevel

class MessageStartDateAfterOrEqualIndividStartDate : AbstractRule(
    WarningLevel.ERROR,
    "Melding Kontroll 2e: Startdato mot individets startdato"
) {

    override fun validate(context: ValidationContext): List<ReportEntry>? {
        TODO("Not yet implemented")
    }
}