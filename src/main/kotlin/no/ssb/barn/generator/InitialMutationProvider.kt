package no.ssb.barn.generator

import no.ssb.barn.xsd.BarnevernType
import java.time.LocalDate
import java.time.ZonedDateTime
import java.util.*

object InitialMutationProvider {

    @JvmStatic
    fun createInitialMutation(currentDate: ZonedDateTime): BarnevernType =
        BarnevernType(id = UUID.randomUUID())
            .apply {

                datoUttrekk = currentDate.plusHours(
                    (8..20).random().toLong()
                )
                fagsystem = RandomUtils.generateRandomFagsystemType()
                avgiver = RandomUtils.generateRandomAvgiverType()

                val socialSecurityId = RandomUtils.generateRandomSSN(
                    LocalDate.now().minusYears(20),
                    LocalDate.now().minusYears(1)
                )

                sak.apply {
                    startDato = currentDate
                    fodselsnummer = socialSecurityId
                    fodseldato = RandomUtils.getDateOfBirthFromSsn(socialSecurityId)
                    kjonn = RandomUtils.getGenderFromSsn(socialSecurityId)

                    RandomUtils.generateRandomMeldingType(currentDate)
                        .also { melding ->
                            this.melding.add(melding)
                        }
                }
            }
}