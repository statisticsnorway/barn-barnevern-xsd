package no.ssb.barn.report

data class ReportEntry(
    val warningLevel: WarningLevel,
    val ruleName: String,
    val errorText: String,
    val type: String,
    val id: String? = null
)