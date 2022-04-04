package no.ssb.barn.xsd

import java.time.LocalDate

data class CodeListItem(
    val code: String,
    val description: String,
    val validFrom: LocalDate = LocalDate.of(2000, 1, 1),
    val validTo: LocalDate = LocalDate.of(2100, 1, 1),
    val changeDescription: String = ""
)