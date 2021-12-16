package no.ssb.barn.generator

import no.ssb.barn.converter.BarnevernConverter
import no.ssb.barn.xsd.BarnevernType
import no.ssb.barn.xsd.MelderType
import no.ssb.barn.xsd.SaksinnholdType
import java.time.LocalDate

class InitialMutationProvider(xmlResourceName: String) {

    constructor() : this("/initial_mutation.xml")

    private val initialMutationXml: String = getResourceAsText(xmlResourceName)

    fun createInitialMutation(currentDate: LocalDate): BarnevernType {
        val currentDateTime =
            currentDate.atStartOfDay().plusHours((6..20).random().toLong())

        return BarnevernConverter.unmarshallXml(initialMutationXml).apply {
            avgiver = RandomUtils.generateRandomAvgiverType()
            fagsystem = RandomUtils.generateRandomFagsystemType()

            sak.apply {
                startDato = currentDateTime.toLocalDate()
                id = java.util.UUID.randomUUID()
                journalnummer =
                    RandomUtils.generateRandomString(15)
                fodselsnummer = RandomUtils.generateRandomSSN(
                    LocalDate.now().minusYears(20),
                    LocalDate.now().minusYears(1)
                )
            }

            if (!sak.virksomhet.any()) {
                return this
            }

            sak.virksomhet.first().apply {
                startDato = currentDateTime.toLocalDate()
                organisasjonsnummer =
                    avgiver.organisasjonsnummer // TODO: Oslo
            }

            if (!sak.virksomhet.first().melding.any()) {
                return this
            }

            sak.virksomhet.first().melding.first().apply {
                id = java.util.UUID.randomUUID()
                startDato = currentDateTime.toLocalDate()

                melder = mutableListOf(
                    MelderType(
                        MelderType.getRandomCode(
                            currentDateTime.toLocalDate()
                        )
                    )
                )

                saksinnhold = mutableListOf(
                    SaksinnholdType(
                        SaksinnholdType.getRandomCode(
                            currentDateTime.toLocalDate()
                        )
                    )
                )
            }
        }
    }

    companion object {
        private fun getResourceAsText(path: String): String =
            InitialMutationProvider::class.java.getResource(path)!!.readText()
    }
}