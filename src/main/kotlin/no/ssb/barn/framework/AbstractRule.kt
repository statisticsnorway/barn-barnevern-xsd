package no.ssb.barn.framework

import no.ssb.barn.report.ReportEntry
import no.ssb.barn.report.WarningLevel

abstract class AbstractRule(
    private val warningLevel: WarningLevel,
    private val ruleName: String
) {
    abstract fun validate(context: ValidationContext): ReportEntry?

    protected fun createReportEntry(message: String): ReportEntry =
        ReportEntry(
            warningLevel = warningLevel,
            ruleName = ruleName,
            message = message
        )
}