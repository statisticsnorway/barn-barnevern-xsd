package no.ssb.barn.validation.rule

import no.ssb.barn.framework.AbstractRule
import no.ssb.barn.framework.ValidationContext
import no.ssb.barn.report.ReportEntry
import no.ssb.barn.report.WarningLevel
import no.ssb.barn.xsd.TiltakType

class TodoMeasureMultipleAllocationsWithinPeriod : AbstractRule(
    WarningLevel.WARNING,
    "Tiltak Kontroll 9: Flere plasseringstiltak i samme periode",
    TiltakType::class.java.simpleName
) {
    override fun validate(context: ValidationContext): List<ReportEntry>? {
        TODO("Not yet implemented")
    }
}