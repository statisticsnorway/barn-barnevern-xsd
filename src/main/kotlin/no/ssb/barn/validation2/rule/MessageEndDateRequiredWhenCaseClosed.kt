package no.ssb.barn.validation2.rule

import no.ssb.barn.framework.AbstractRule
import no.ssb.barn.framework.ValidationContext
import no.ssb.barn.report.ReportEntry
import no.ssb.barn.report.WarningLevel

class MessageEndDateRequiredWhenCaseClosed : AbstractRule(
    WarningLevel.ERROR,
    "Melding Kontroll 2d: Avslutta 31 12 medfører at sluttdato skal være satt på"
) {

    override fun validate(context: ValidationContext): List<ReportEntry>? {
        TODO("Not yet implemented")
    }
}