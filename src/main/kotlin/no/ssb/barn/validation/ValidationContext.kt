package no.ssb.barn.validation

import no.ssb.barn.xsd.AvgiverType
import no.ssb.barn.xsd.BarnevernType
import no.ssb.barn.xsd.FagsystemType
import no.ssb.barn.xsd.SakType
import java.time.LocalDate
import java.time.ZonedDateTime
import java.util.*

data class ValidationContext(
    val messageId: String,
    val xml: String,
    val rootObject: BarnevernType
) {
    constructor(messageId: String, xml: String) : this(
        messageId,
        xml,
        BarnevernType(
            id = UUID.randomUUID(),
            forrigeId = null,
            datoUttrekk = ZonedDateTime.now(),
            fagsystem = FagsystemType(
                leverandor = "~leverandor~",
                navn = "~navn~",
                versjon = "~versjon~"
            ),
            avgiver = AvgiverType(
                organisasjonsnummer = "123456789",
                kommunenummer = "0301",
                kommunenavn = "OSLO",
                bydelsnummer = null,
                bydelsnavn = null
            ),
            sak = SakType(
                id = UUID.randomUUID(),
                migrertId = null,
                startDato = LocalDate.now(),
                sluttDato = null,
                journalnummer = "~journalnummer~",
                fodselsnummer = "12345612345",
                duFnummer = null,
                fodseldato = LocalDate.now().minusYears(1),
                kjonn = "1",
                avsluttet = null
            )
        )
    )
}
