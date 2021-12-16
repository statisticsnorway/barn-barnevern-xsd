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

        return BarnevernConverter.unmarshallXml(initialMutationXml)
            .apply outerApply@{
                fagsystem = RandomUtils.generateRandomFagsystemType()
                avgiver = RandomUtils.generateRandomAvgiverType()

                sak.apply {
                    startDato = currentDateTime.toLocalDate()
                    id = java.util.UUID.randomUUID()
                    journalnummer =
                        RandomUtils.generateRandomString(15)
                    fodselsnummer = RandomUtils.generateRandomSSN(
                        LocalDate.now().minusYears(20),
                        LocalDate.now().minusYears(1)
                    )

                    if (!virksomhet.any()) {
                        return@outerApply
                    }
                }

                sak.virksomhet.first().also {
                    RandomUtils.copyAvgiverToVirksomhet(avgiver, it)
                    it.startDato = currentDateTime.toLocalDate()

                    if (!it.melding.any()) {
                        return@outerApply
                    }
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