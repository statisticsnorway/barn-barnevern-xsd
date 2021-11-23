package no.ssb.barn.codelists

import java.time.*

data class CodelistItem(
    var code: String,
    var description: String,
    var validFrom: LocalDate = LocalDate.of(2000, 1, 1),
    var validTo: LocalDate = LocalDate.of(2100, 1, 1),
    var changeDescription: String = ""
)