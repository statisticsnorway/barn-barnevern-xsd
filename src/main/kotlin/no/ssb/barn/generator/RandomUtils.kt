package no.ssb.barn.generator

import no.ssb.barn.util.ValidationUtils.controlSumDigits1
import no.ssb.barn.util.ValidationUtils.controlSumDigits2
import no.ssb.barn.util.ValidationUtils.modulo11
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Suppress("SpellCheckingInspection")
object RandomUtils {

    const val FEMALE = "2"
    const val MALE = "1"

    @JvmStatic
    fun generateRandomIntFromRange(
        startInclusive: Int,
        endExclusive: Int
    ): Int =
        (startInclusive until endExclusive).random()

    @JvmStatic
    fun generateRandomLongFromRange(
        startInclusive: Long,
        endExclusive: Long
    ): Long =
        (startInclusive until endExclusive).random()

    private val charPool: List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')

    @JvmStatic
    fun generateRandomString(stringLength: Int): String =
        (1..stringLength)
            .map { generateRandomIntFromRange(0, charPool.size) }
            .map(charPool::get)
            .joinToString("")


    private const val SSN_LENGTH = 11
    private const val SSN_INITIAL_SEED_LENGTH = 9

    @JvmStatic
    fun generateRandomSSN(
        startInclusive: LocalDate,
        endExclusive: LocalDate
    ): String =
        generateRandomLongFromRange(
            startInclusive.toEpochDay(),
            endExclusive.toEpochDay()
        ).let { randomDay ->
            LocalDate.ofEpochDay(randomDay)
                .format(DateTimeFormatter.ofPattern("ddMMyy"))
        }.let { birthDate123456 ->
            generateSequence {
                birthDate123456
                    .plus(generateRandomIntFromRange(100, 499).toString())
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
            }
                .filter { ssnCandidate -> ssnCandidate.length == SSN_LENGTH }
                .first()
        }

    private fun buildSsnRecursive(
        seed: String, controlDigits: List<List<Int>>
    ): String =
        if (seed.length == SSN_LENGTH) {
            seed
        } else {
            modulo11(
                seed,
                controlDigits[seed.length - SSN_INITIAL_SEED_LENGTH]
            ).let { modulo11 ->
                if (modulo11 == 1 || modulo11 == 10) {
                    seed
                } else {
                    // NOTE: Recursive call
                    buildSsnRecursive(
                        seed.plus(getModAsString(modulo11)),
                        controlDigits
                    )
                }
            }
        }

    private fun getModAsString(value: Int): String =
        (if (value == 0) 0 else SSN_LENGTH - value).toString()
}