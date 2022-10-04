package no.ssb.barn.util

import no.ssb.barn.util.Shared.DATE_TIME_FORMATTER
import no.ssb.barn.util.Shared.controlSumDigits1
import no.ssb.barn.util.Shared.controlSumDigits2
import no.ssb.barn.util.ValidationUtils.modulo11
import java.time.LocalDate

object RandomUtils {

    fun generateRandomString(stringLength: Int): String =
        (1..stringLength)
            .map { CHAR_POOL.indices.random() }
            .map(CHAR_POOL::get)
            .joinToString("")

    fun generateRandomSSN(
        startInclusive: LocalDate,
        endExclusive: LocalDate
    ): String = (startInclusive.toEpochDay() until endExclusive.toEpochDay()).random()
        .let { randomDay ->
            LocalDate.ofEpochDay(randomDay).format(DATE_TIME_FORMATTER)
        }.let { birthDate123456 ->
            generateSequence {
                birthDate123456.plus((100 until 499).random().toString())
            }.map { nineDigitSeed ->
                buildSsnRecursive(
                    nineDigitSeed,
                    listOf(
                        controlSumDigits1.subList(
                            0, controlSumDigits1.size - 1
                        ),
                        controlSumDigits2.subList(
                            0, controlSumDigits2.size - 1
                        )
                    )
                )
            }.filter { ssnCandidate -> ssnCandidate.length == SSN_LENGTH }.first()
        }

    private val CHAR_POOL: List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')

    private const val SSN_LENGTH = 11
    private const val SSN_INITIAL_SEED_LENGTH = 9

    private fun buildSsnRecursive(
        seed: String,
        controlDigits: List<List<Int>>
    ): String = if (seed.length == SSN_LENGTH) {
        seed
    } else {
        modulo11(
            seed,
            controlDigits[seed.length - SSN_INITIAL_SEED_LENGTH]
        ).let { modulo11 ->
            if (modulo11 == 1 || modulo11 == 10) {
                seed
            } else {
                /** NOTE: Recursive call */
                buildSsnRecursive(
                    seed = seed.plus(getModAsString(modulo11)),
                    controlDigits = controlDigits
                )
            }
        }
    }

    private fun getModAsString(value: Int): String =
        (if (value == 0) 0 else SSN_LENGTH - value).toString()
}
