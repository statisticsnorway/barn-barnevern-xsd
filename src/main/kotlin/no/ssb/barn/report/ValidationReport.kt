package no.ssb.barn.report

data class ValidationReport(
    val journalId: String,
    val individualId: String,
    val reportEntries: List<ReportEntry>
)