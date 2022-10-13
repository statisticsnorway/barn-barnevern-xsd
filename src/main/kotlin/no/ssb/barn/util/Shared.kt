package no.ssb.barn.util

import java.time.format.DateTimeFormatter
import java.util.regex.Pattern

object Shared {

    val controlSumDigits1 = listOf(3, 7, 6, 1, 8, 9, 4, 5, 2, 1)
    val controlSumDigits2 = listOf(5, 4, 3, 2, 7, 6, 5, 4, 3, 2, 1)

    val DATE_TIME_FORMATTER: DateTimeFormatter = DateTimeFormatter.ofPattern("ddMMyy")

    fun modulo11(toCheck: String, controlDigits: List<Int>): Int =
        if (toCheck.length == controlDigits.size && Pattern.compile("^\\d+$").matcher(toCheck).matches()) {
            toCheck.asSequence()
                .map { it.toString().toInt() }
                .zip(controlDigits.asSequence()) { digit, controlDigit -> digit * controlDigit }
                .sum()
                .mod(11)
        } else {
            -1
        }
}
