package no.ssb.barn.generator

import no.ssb.barn.deserialize.BarnevernDeserializer
import no.ssb.barn.xsd.BarnevernType
import no.ssb.barn.xsd.MelderType
import no.ssb.barn.xsd.SaksinnholdType
import java.time.LocalDate
import java.time.LocalDateTime

class TestDataGenerator {

    private val initialMutationXml = getResourceAsText("/initial_mutation.xml")

    fun createInitialMutation(): BarnevernType {
        val instance = BarnevernDeserializer.unmarshallXml(initialMutationXml)
        var globalCompanyId: String

        with(instance) {
            datoUttrekk = LocalDateTime.now()
            fagsystem = RandomUtils.generateRandomFagsystemType()
            avgiver = RandomUtils.generateRandomAvgiverType()
            globalCompanyId = avgiver.organisasjonsnummer
        }

        with(instance.sak) {
            startDato = LocalDate.now().minusDays(1)
            id = java.util.UUID.randomUUID()
            journalnummer =
                RandomUtils.generateRandomString(15)
            fodselsnummer = RandomUtils.generateRandomSSN(
                LocalDate.now().minusYears(20),
                LocalDate.now().minusYears(1)
            )
        }

        with(instance.sak.virksomhet[0]) {

            startDato = LocalDate.now()
            organisasjonsnummer = globalCompanyId // TODO: Oslo
            plan = null
            undersokelse = null
            tiltak = null
            vedtak = null
            ettervern = null
            oversendelseBarneverntjeneste = null
            flytting = null
            relasjon = null

            val messages = melding
            if (messages != null && messages.size > 0) {
                with(messages[0]) {
                    id = java.util.UUID.randomUUID()
                    startDato = LocalDate.now()

                    melder = mutableListOf(
                        MelderType(
                            MelderType.getRandomCode(
                                LocalDate.now()
                            )
                        )
                    )

                    saksinnhold = mutableListOf(
                        SaksinnholdType(
                            SaksinnholdType.getRandomCode(
                                LocalDate.now()
                            )
                        )
                    )
                }
            }
        }

        return instance
    }

    fun mutate(incoming: BarnevernType): BarnevernType {
        return incoming

        // 1. Check state of incoming. If impossible to mutate, return null

        // 2. Get list of available next states for incoming based on state
        //    for incoming.

        // 3. Select a random one, mutate incoming and return mutated version.
    }

    private fun getResourceAsText(path: String): String =
        javaClass.getResource(path).readText()
}