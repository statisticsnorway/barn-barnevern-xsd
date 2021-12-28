package no.ssb.barn.report

data class ValidationReport(
    val messageId: String,
    val reportEntries: List<ReportEntry>,
    val severity: WarningLevel,
    val instanceAsMap: Map<String, Any>
)