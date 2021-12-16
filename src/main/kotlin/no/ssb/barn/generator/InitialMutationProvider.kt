package no.ssb.barn.generator

import no.ssb.barn.converter.BarnevernConverter
import no.ssb.barn.xsd.BarnevernType
import no.ssb.barn.xsd.MelderType
import no.ssb.barn.xsd.SaksinnholdType
import java.time.LocalDate

class InitialMutationProvider(xmlResourceName: String) {

    constructor() : this("/initial_mutation.xml")

    private val initialMutationXml: String = getResourceAsText(xmlResourceName)

    fun createInitialMutation(currentDate: LocalDate): BarnevernType =
        BarnevernConverter.unmarshallXml(initialMutationXml)
            .apply outerApply@{

                datoUttrekk = currentDate.atStartOfDay().plusHours(
                    (8..20).random().toLong()
                )
                fagsystem = RandomUtils.generateRandomFagsystemType()
                avgiver = RandomUtils.generateRandomAvgiverType()

                sak.apply {
                    startDato = currentDate
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

                // all props in BarnevernType handled above

                sak.virksomhet.first().also {
                    RandomUtils.copyAvgiverToVirksomhet(avgiver, it)
                    it.startDato = currentDate

                    if (!it.melding.any()) {
                        return@outerApply
                    }
                }

                sak.virksomhet.first().melding.first().apply {
                    id = java.util.UUID.randomUUID()
                    startDato = currentDate

                    melder = mutableListOf(
                        MelderType(MelderType.getRandomCode(currentDate))
                    )

                    saksinnhold = mutableListOf(
                        SaksinnholdType(
                            SaksinnholdType.getRandomCode(currentDate)
                        )
                    )
                }
            }

    companion object {
        private fun getResourceAsText(path: String): String =
            InitialMutationProvider::class.java.getResource(path)!!.readText()
    }
}