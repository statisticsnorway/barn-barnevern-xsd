package no.ssb.barn.util

import no.ssb.barn.codelists.CodeListItem
import java.time.LocalDate

class TypeUtils {

    companion object {

        fun getCodes(
            date: LocalDate,
            codeList: List<CodeListItem>
        ): List<CodeListItem> =
            codeList.filter {
                (date.isEqual(it.validFrom) || date.isAfter(it.validFrom))
                        && (date.isBefore(it.validTo) || date.isEqual(it.validTo))
            }
    }
}