package no.ssb.barn.report

data class ReportEntry(
    val warningLevel: WarningLevel,
    val ruleName: String,
    val errorText: String,
    val xmlContext: String? = null, // TODO: Make non-nullable
    val contextId: String? = null, // TODO: Make non-nullable
    val errorDetails: String? = null
)