package no.ssb.barn.framework

import no.ssb.barn.framework.ValidationContext
import no.ssb.barn.report.ReportEntry
import no.ssb.barn.report.WarningLevel

abstract class AbstractRule(
    val warningLevel: WarningLevel,
    val ruleName: String
) {
    abstract fun validate(context: ValidationContext): ReportEntry?
}