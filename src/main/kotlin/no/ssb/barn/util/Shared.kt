package no.ssb.barn.util

import java.time.format.DateTimeFormatter

object Shared {

    val controlSumDigits1 = listOf(3, 7, 6, 1, 8, 9, 4, 5, 2, 1)
    val controlSumDigits2 = listOf(5, 4, 3, 2, 7, 6, 5, 4, 3, 2, 1)

    val DATE_TIME_FORMATTER: DateTimeFormatter = DateTimeFormatter.ofPattern("ddMMyy")
}
