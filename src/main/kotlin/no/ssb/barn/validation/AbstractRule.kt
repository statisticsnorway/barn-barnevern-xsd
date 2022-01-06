package no.ssb.barn.validation

import no.ssb.barn.report.ReportEntry
import no.ssb.barn.report.WarningLevel
import java.util.*

abstract class AbstractRule(
    private val warningLevel: WarningLevel,
    private val ruleName: String,
    private val xmlContext: String
) {
    abstract fun validate(context: ValidationContext): List<ReportEntry>?

    protected fun createSingleReportEntryList(
        errorText: String,
        contextId: UUID
    ): List<ReportEntry> =
        listOf(createReportEntry(errorText, contextId))

    protected fun createReportEntry(
        errorText: String,
        contextId: UUID
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
            errorText = errorText,
            type = xmlContext.removeSuffix("Type"),
        )
}