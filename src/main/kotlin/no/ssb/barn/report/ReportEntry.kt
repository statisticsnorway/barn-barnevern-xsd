package no.ssb.barn.report

data class ReportEntry(
    val warningLevel: WarningLevel,
    val ruleName: String,
    val message: String
)