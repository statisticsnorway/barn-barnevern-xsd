package no.ssb.barn.report

data class ReportEntry(
    val warningLevel: WarningLevel,
    val ruleName: String,
    val errorText: String,
    val type: String? = null, // TODO: Make non-nullable
    val id: String? = null // TODO: Make non-nullable
)