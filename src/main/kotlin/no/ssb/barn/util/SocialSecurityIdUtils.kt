package no.ssb.barn.util

import no.ssb.barn.util.Shared.DATE_TIME_FORMATTER
import java.time.LocalDate

object SocialSecurityIdUtils {

    fun getGenderFromSsn(socialSecurityId: String): String =
        if (socialSecurityId.substring(8, 9).toInt() % 2 == 0) FEMALE else MALE

    fun getDateOfBirthFromSsn(socialSecurityId: String): LocalDate {
        val parsedDate = LocalDate.parse(socialSecurityId.substring(0, 6), DATE_TIME_FORMATTER)
        return if (parsedDate.isAfter(LocalDate.now())) parsedDate.minusYears(100) else parsedDate
    }

    const val FEMALE = "2"
    const val MALE = "1"
}
