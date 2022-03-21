package no.ssb.barn.generator

import no.ssb.barn.generator.RandomTypeProvider.generateRandomAvgiverType
import no.ssb.barn.generator.RandomTypeProvider.generateRandomFagsystemType
import no.ssb.barn.generator.RandomTypeProvider.generateRandomMeldingType
import no.ssb.barn.generator.SocialSecurityIdUtils.getDateOfBirthFromSsn
import no.ssb.barn.generator.SocialSecurityIdUtils.getGenderFromSsn
import no.ssb.barn.xsd.BarnevernType
import no.ssb.barn.xsd.SakType
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
                fagsystem = generateRandomFagsystemType()
                avgiver = generateRandomAvgiverType()

                val socialSecurityId = RandomUtils.generateRandomSSN(
                    LocalDate.now().minusYears(20),
                    LocalDate.now().minusYears(1)
                )

                sak = SakType(id = UUID.randomUUID())

                sak.apply {
                    startDato = currentDate
                    fodselsnummer = socialSecurityId
                    fodseldato = getDateOfBirthFromSsn(socialSecurityId)
                    kjonn = getGenderFromSsn(socialSecurityId)

                    generateRandomMeldingType(currentDate)
                        .also { melding ->
                            this.melding.add(melding)
                        }
                }
            }
}