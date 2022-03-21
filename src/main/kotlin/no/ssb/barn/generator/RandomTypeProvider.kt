package no.ssb.barn.generator

import no.ssb.barn.xsd.*
import java.time.ZonedDateTime

object RandomTypeProvider {

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
            RandomData.avgiverTypes.first { it.kommunenummer == GeneratorConstants.OSLO }
                .copy()
                .apply {
                    val cityPart = RandomData.cityPartsOslo.entries.random()
                    bydelsnummer = cityPart.key
                    bydelsnavn = cityPart.value
                }
        } else {
            RandomData.avgiverTypes
                .filter { it.kommunenummer != GeneratorConstants.OSLO }
                .random()
        }
    }
}