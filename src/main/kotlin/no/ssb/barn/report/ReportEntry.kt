package no.ssb.barn.report

data class ReportEntry(
    val warningLevel: WarningLevel,
    val ruleName: String,
    val errorText: String,
    val errorDetails: String? = null
)