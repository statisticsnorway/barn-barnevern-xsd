package no.ssb.barn.util

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import java.util.regex.Pattern

object SocialSecurityIdUtils {

    /**
     * Extracts gender as [String] from social security id.
     *
     * @param socialSecurityId 11 digit social security id.
     * @return '2' if female, '1' if male. Fallback is '1' (male).
     * @throws [IllegalArgumentException] if the social security id is on wrong format.
     */
    fun extractGenderFromSocialSecurityId(socialSecurityId: String): String = socialSecurityId
        .takeIf { socialSecurityIdPattern.matcher(it).matches() }?.let {
            if (it.substring(8, 9).toInt() % 2 == 0) FEMALE
            else MALE
        } ?: throw IllegalArgumentException(INVALID_SSN_FORMAT_MSG)

    /**
     * Extracts date of birth as [LocalDate] from social security id. When date of birth is in the future,
     * 100 years is subtracted before returning the date.
     *
     * @param socialSecurityId 11 digit social security id.
     * @return [LocalDate]
     * @throws [IllegalArgumentException] if the social security id is on wrong format.
     * @throws [DateTimeParseException] if date of birth cannot be parsed.
     */
    fun extractDateOfBirthFromSocialSecurityId(socialSecurityId: String): LocalDate = socialSecurityId
        .takeIf { socialSecurityIdPattern.matcher(it).matches() }?.let {
            LocalDate.parse(it.substring(0, DATE_OF_BIRTH_LEN), TWO_DIGIT_YEAR_DATE_TIME_FORMATTER)
                .let { parsedDate ->
                    if (parsedDate.isAfter(LocalDate.now())) parsedDate.minusYears(100)
                    else parsedDate
                }
        } ?: throw IllegalArgumentException(INVALID_SSN_FORMAT_MSG)

    const val FEMALE = "2"
    const val MALE = "1"

    internal const val INVALID_SSN_FORMAT_MSG = "Invalid social security id format"

    private val socialSecurityIdPattern = Pattern.compile("^\\d{11}$")
    private const val DATE_OF_BIRTH_LEN = 6

    internal val TWO_DIGIT_YEAR_DATE_TIME_FORMATTER: DateTimeFormatter = DateTimeFormatter.ofPattern("ddMMyy")
}
