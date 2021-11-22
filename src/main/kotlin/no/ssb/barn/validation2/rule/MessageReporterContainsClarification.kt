package no.ssb.barn.validation2.rule

import no.ssb.barn.framework.AbstractRule
import no.ssb.barn.framework.ValidationContext
import no.ssb.barn.report.ReportEntry
import no.ssb.barn.report.WarningLevel

class MessageReporterContainsClarification : AbstractRule(
    WarningLevel.ERROR,
    "Melder Kontroll 2: Kontroll av kode og presisering"
) {

    override fun validate(context: ValidationContext): List<ReportEntry>? {
        TODO("Not yet implemented")
    }
}