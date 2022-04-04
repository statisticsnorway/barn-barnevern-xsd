package no.ssb.barn.util

import java.time.LocalDate
import java.time.format.DateTimeFormatter

object SocialSecurityIdUtils {

    const val FEMALE = "2"
    const val MALE = "1"

    @JvmStatic
    fun getGenderFromSsn(socialSecurityId: String): String =
        if (socialSecurityId.substring(8, 9).toInt() % 2 == 0) FEMALE else MALE

    @JvmStatic
    fun getDateOfBirthFromSsn(socialSecurityId: String): LocalDate {
        val parsedDate = LocalDate.parse(socialSecurityId.substring(0, 6), DateTimeFormatter.ofPattern("ddMMyy"))
        return if (parsedDate.isAfter(LocalDate.now())) parsedDate.minusYears(100) else parsedDate
    }
}