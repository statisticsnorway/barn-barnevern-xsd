package no.ssb.barn.generator

import no.ssb.barn.generator.RandomData.avgiverTypes
import no.ssb.barn.generator.RandomData.cityPartsOslo
import no.ssb.barn.util.ValidationUtils.controlSumDigits1
import no.ssb.barn.util.ValidationUtils.controlSumDigits2
import no.ssb.barn.util.ValidationUtils.modulo11
import no.ssb.barn.xsd.*
import java.time.LocalDate
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

@Suppress("SpellCheckingInspection")
object RandomUtils {

    const val FEMALE = "2"
    const val MALE = "1"

    @JvmStatic
    fun getGenderFromSsn(socialSecurityId: String): String =
        if (socialSecurityId.substring(8, 9).toInt() % 2 == 0) FEMALE else MALE

    @JvmStatic
    fun getDateOfBirthFromSsn(socialSecurityId: String): LocalDate =
        LocalDate.parse(socialSecurityId.substring(0, 6), DateTimeFormatter.ofPattern("ddMMyy"))

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

    @JvmStatic
    fun generateRandomFagsystemType(): FagsystemType =
        listOf(
            FagsystemType("Visma", "Flyt Barnevern", "1.0.0"),
            FagsystemType("Netcompany", "Modulus Barn", "0.1.0"),
        ).random()

    @JvmStatic
    fun generateRandomMeldingType(currentDate: ZonedDateTime): MeldingType =
        MeldingType().apply {
            id = java.util.UUID.randomUUID()
            startDato = currentDate
            melder.add(MelderType(MelderType.getRandomCode(currentDate.toLocalDate())))
            saksinnhold.add(
                SaksinnholdType(
                    SaksinnholdType.getRandomCode(currentDate.toLocalDate())
                )
            )
        }

    @JvmStatic
    fun generateRandomAvgiverType(): AvgiverType {
        // make approx. 29% chance of selecting Oslo
        return if ((1..5).random() == 1) {
            avgiverTypes.first { it.kommunenummer == GeneratorConstants.OSLO }
                .copy()
                .apply {
                    val cityPart = cityPartsOslo.entries.random()
                    bydelsnummer = cityPart.key
                    bydelsnavn = cityPart.value
                }
        } else {
            avgiverTypes
                .filter { it.kommunenummer != GeneratorConstants.OSLO }
                .random()
        }
    }
}