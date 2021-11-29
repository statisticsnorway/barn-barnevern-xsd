package no.ssb.barn.validation.rule

import no.ssb.barn.framework.AbstractRule
import no.ssb.barn.framework.ValidationContext
import no.ssb.barn.report.ReportEntry
import no.ssb.barn.report.WarningLevel

class TodoMessageEndDateRequiredWhenCaseClosed : AbstractRule(
    WarningLevel.ERROR,
    "Melding Kontroll 2d: Avslutta 31 12 medfører at sluttdato skal være satt på",
    "TODO"
) {

    override fun validate(context: ValidationContext): List<ReportEntry>? {
        TODO("Not yet implemented")
    }
}