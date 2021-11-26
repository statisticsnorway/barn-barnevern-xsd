package no.ssb.barn.framework

import no.ssb.barn.report.ReportEntry
import no.ssb.barn.report.WarningLevel

abstract class AbstractRule(
    private val warningLevel: WarningLevel,
    private val ruleName: String,
    private val xmlContext: String? = null
) {
    abstract fun validate(context: ValidationContext): List<ReportEntry>?

    protected fun createSingleReportEntryList(
        errorText: String, errorDetails: String? = null
    ): List<ReportEntry> =
        listOf(createReportEntry(errorText, errorDetails))

    protected fun createReportEntry(
        errorText: String,
        contextId: String,
        errorDetails: String? = null
    ): ReportEntry =
        ReportEntry(
            warningLevel = warningLevel,
            ruleName = ruleName,
            errorText = errorText,
            type = xmlContext?.removeSuffix("Type"),
            id = contextId,
            errorDetails = errorDetails
        )

    protected fun createReportEntry(
        errorText: String,
        errorDetails: String? = null
    ): ReportEntry =
        ReportEntry(
            warningLevel = warningLevel,
            ruleName = ruleName,
            errorText = errorText,
            errorDetails = errorDetails
        )
}