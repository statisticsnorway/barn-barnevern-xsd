package no.ssb.barn.generator

import java.time.LocalDate
import java.time.format.DateTimeFormatter

object SocialSecurityIdUtils {

    @JvmStatic
    fun getGenderFromSsn(socialSecurityId: String): String =
        if (socialSecurityId.substring(8, 9).toInt() % 2 == 0) RandomUtils.FEMALE else RandomUtils.MALE

    @JvmStatic
    fun getDateOfBirthFromSsn(socialSecurityId: String): LocalDate =
        LocalDate.parse(socialSecurityId.substring(0, 6), DateTimeFormatter.ofPattern("ddMMyy"))
}