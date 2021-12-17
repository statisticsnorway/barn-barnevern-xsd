package no.ssb.barn.generator

import no.ssb.barn.xsd.BarnevernType
import java.time.LocalDate

object InitialMutationProvider {

    @JvmStatic
    fun createInitialMutation(currentDate: LocalDate): BarnevernType =
        BarnevernType()
            .apply {

                datoUttrekk = currentDate.atStartOfDay().plusHours(
                    (8..20).random().toLong()
                )
                fagsystem = RandomUtils.generateRandomFagsystemType()
                avgiver = RandomUtils.generateRandomAvgiverType()

                sak.apply {
                    startDato = currentDate
                    fodselsnummer = RandomUtils.generateRandomSSN(
                        LocalDate.now().minusYears(20),
                        LocalDate.now().minusYears(1)
                    )
                }

                // START sak -> virksomhet

                RandomUtils.generateRandomVirksomhetType(avgiver)
                    .also { virksomhet ->
                        virksomhet.startDato = currentDate
                        sak.virksomhet.add(virksomhet)

                        // START sak -> virksomhet -> melding

                        RandomUtils.generateRandomMeldingType(currentDate)
                            .also { melding ->
                                virksomhet.melding.add(melding)
                            }
                    }
            }
}