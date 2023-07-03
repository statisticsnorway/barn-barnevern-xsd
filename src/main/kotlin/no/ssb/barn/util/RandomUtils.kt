package no.ssb.barn.util

import no.ssb.barn.util.RandomUtils.generateRandomString
import no.ssb.barn.util.Shared.TWO_DIGIT_YEAR_DATE_TIME_FORMATTER
import java.time.LocalDate
import java.util.regex.Pattern

object RandomUtils {

    /**
     * Generates a random string of [length] characters consisting of characters a-z, A-Z and 0-9.
     *
     * @param length the length of the string to generate
     * @return a random string of [length]
     * @throws [IllegalArgumentException] if [length] is less than 1
     */
    fun generateRandomString(length: Int): String = when {
        length > 0 -> {
            (1..length)
                .map { charPool.indices.random() }
                .map(charPool::get)
                .joinToString(EMPTY_STRING)
        }

        else -> throw IllegalArgumentException(INVALID_LEN_MESSAGE)
    }

    /**
     * Generates a random social security id (fødselsnummer) with date of birth in the
     * range [startInclusive, endExclusive].
     *
     * @param startInclusive the start of the range (inclusive)
     * @param endExclusive the end of the range (exclusive)
     * @return a valid, random social security id (fødselsnummer)
     * @throws [IllegalArgumentException] if [endExclusive] is not after [startInclusive]
     */
    fun generateRandomSocialSecurityId(
        startInclusive: LocalDate,
        endExclusive: LocalDate
    ): String = when {
        startInclusive.isBefore(endExclusive) -> {
            (startInclusive.toEpochDay() until endExclusive.toEpochDay()).random()
                .let { randomDay -> LocalDate.ofEpochDay(randomDay).format(TWO_DIGIT_YEAR_DATE_TIME_FORMATTER) }
                .let { sixDigitBirthDate ->
                    generateSequence { sixDigitBirthDate.plus((100 until 499).random().toString()) }
                        .map { nineDigitSeed ->
                            buildSocialSecurityIdRecursive(
                                seed = nineDigitSeed,
                                controlDigits = listOf(
                                    controlSumDigits1.subList(0, controlSumDigits1.size - 1),
                                    controlSumDigits2.subList(0, controlSumDigits2.size - 1)
                                )
                            )
                        }.filter { candidate -> candidate.length == SOCIAL_SECURITY_ID_LENGTH }.first()
                }
        }

        else -> throw IllegalArgumentException(INVALID_DATE_RANGE_MESSAGE)
    }

    private fun buildSocialSecurityIdRecursive(
        seed: String,
        controlDigits: List<List<Int>>
    ): String = if (seed.length == SOCIAL_SECURITY_ID_LENGTH) {
        /** break the recursion when length is correct */
        seed
    } else {
        modulo11(
            socialSecurityIdToCheck = seed,
            controlDigits = controlDigits[seed.length - SOCIAL_SECURITY_ID_INITIAL_SEED_LENGTH]
        ).let { modulo11 ->
            if (modulo11 == 1 || modulo11 == 10) {
                seed
            } else {
                /** NOTE: Recursive call */
                buildSocialSecurityIdRecursive(
                    seed = seed.plus(getModAsString(modulo11)),
                    controlDigits = controlDigits
                )
            }
        }
    }

    internal fun modulo11(
        socialSecurityIdToCheck: String,
        controlDigits: List<Int>
    ): Int = if (socialSecurityIdToCheck.length == controlDigits.size
        && digitPattern.matcher(socialSecurityIdToCheck).matches()
    ) {
        socialSecurityIdToCheck.asSequence()
            .map { it.toString().toInt() }
            .zip(controlDigits.asSequence()) { digit, controlDigit -> digit * controlDigit }
            .sum()
            .mod(11)
    } else -1

    internal const val INVALID_DATE_RANGE_MESSAGE = "endInclusive must be later than startInclusive"
    internal const val INVALID_LEN_MESSAGE = "length must be greater than 0"

    private const val EMPTY_STRING = ""
    private const val SOCIAL_SECURITY_ID_LENGTH = 11
    private const val SOCIAL_SECURITY_ID_INITIAL_SEED_LENGTH = 9

    /** used by [generateRandomString] */
    private val charPool: List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')

    internal val controlSumDigits1 = listOf(3, 7, 6, 1, 8, 9, 4, 5, 2, 1)
    internal val controlSumDigits2 = listOf(5, 4, 3, 2, 7, 6, 5, 4, 3, 2, 1)

    private val digitPattern = Pattern.compile("^\\d+$")

    private fun getModAsString(value: Int): String =
        (if (value == 0) 0 else SOCIAL_SECURITY_ID_LENGTH - value).toString()
}
