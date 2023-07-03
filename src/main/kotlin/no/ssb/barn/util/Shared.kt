package no.ssb.barn.util

import java.time.format.DateTimeFormatter

object Shared {
    val TWO_DIGIT_YEAR_DATE_TIME_FORMATTER: DateTimeFormatter = DateTimeFormatter.ofPattern("ddMMyy")
}
