package no.ssb.barn.report

import java.util.*

data class ReportEntry(
    val warningLevel: WarningLevel,
    val ruleName: String,
    val errorText: String,
    val type: String,
    val id: UUID? = null
)