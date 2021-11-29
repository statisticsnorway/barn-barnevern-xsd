package no.ssb.barn.framework

import no.ssb.barn.report.ReportEntry
import no.ssb.barn.report.WarningLevel

abstract class AbstractRule(
    private val warningLevel: WarningLevel,
    private val ruleName: String,
    private val xmlContext: String
) {
    abstract fun validate(context: ValidationContext): List<ReportEntry>?

    protected fun createSingleReportEntryList(
        errorText: String,
        contextId: String
    ): List<ReportEntry> =
        listOf(createReportEntry(errorText, contextId))

    protected fun createReportEntry(
        errorText: String,
        contextId: String
    ): ReportEntry =
        ReportEntry(
            warningLevel = warningLevel,
            ruleName = ruleName,
            errorText = errorText,
            type = xmlContext.removeSuffix("Type"),
            id = contextId
        )

    protected fun createReportEntry(
        errorText: String
    ): ReportEntry =
        ReportEntry(
            warningLevel = warningLevel,
            ruleName = ruleName,
            errorText = errorText
        )
}