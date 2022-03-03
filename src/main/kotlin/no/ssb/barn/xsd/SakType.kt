package no.ssb.barn.xsd

import no.ssb.barn.converter.LocalDateAdapter
import no.ssb.barn.converter.LocalDateTimeAdapter
import no.ssb.barn.converter.UuidAdapter
import no.ssb.barn.generator.RandomUtils
import java.time.LocalDate
import java.time.ZonedDateTime
import java.util.*
import javax.xml.bind.annotation.*
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
    name = "SakType",
    propOrder = ["id", "migrertId", "startDato", "sluttDato",
        "journalnummer", "fodselsnummer", "duFnummer", "fodseldato", "kjonn",
        "avsluttet", "melding", "undersokelse", "plan", "tiltak", "vedtak", "ettervern", "oversendelseBarneverntjeneste",
        "relasjon"]
)
data class SakType(
    @field:XmlAttribute(name = "Id", required = true)
    @field:XmlJavaTypeAdapter(
        UuidAdapter::class
    )
    var id: UUID = UUID.randomUUID(),

    @field:XmlAttribute(name = "MigrertId")
    var migrertId: String? = null,

    @field:XmlAttribute(name = "StartDato", required = true)
    @field:XmlSchemaType(name = "date")
    @field:XmlJavaTypeAdapter(
        LocalDateTimeAdapter::class
    )
    var startDato: ZonedDateTime = ZonedDateTime.now(),

    @field:XmlAttribute(name = "SluttDato")
    @field:XmlSchemaType(name = "date")
    @field:XmlJavaTypeAdapter(
        LocalDateTimeAdapter::class
    )
    var sluttDato: ZonedDateTime? = null,

    @field:XmlAttribute(name = "Journalnummer", required = true)
    var journalnummer: String = RandomUtils.generateRandomString(20),

    @field:XmlAttribute(name = "Fodselsnummer")
    var fodselsnummer: String? = null,

    @field:XmlAttribute(name = "DUFnummer")
    var duFnummer: String? = null,

    @field:XmlAttribute(name = "Fodseldato")
    @field:XmlSchemaType(name = "date")
    @field:XmlJavaTypeAdapter(
        LocalDateAdapter::class
    )
    var fodseldato: LocalDate? = null,

    @field:XmlAttribute(name = "Kjonn")
    var kjonn: String? = null,

    @field:XmlAttribute(name = "Avsluttet")
    var avsluttet: Boolean? = null,

    @field:XmlElement(name = "Melding")
    var melding: MutableList<MeldingType> = mutableListOf(),

    @field:XmlElement(name = "Undersokelse")
    var undersokelse: MutableList<UndersokelseType> = mutableListOf(),

    @field:XmlElement(name = "Plan")
    var plan: MutableList<PlanType> = mutableListOf(),

    @field:XmlElement(name = "Tiltak")
    var tiltak: MutableList<TiltakType> = mutableListOf(),

    @field:XmlElement(name = "Vedtak")
    var vedtak: MutableList<VedtakType> = mutableListOf(),

    @field:XmlElement(name = "Ettervern")
    var ettervern: MutableList<EttervernType> = mutableListOf(),

    @field:XmlElement(name = "OversendelseBarneverntjeneste")
    var oversendelseBarneverntjeneste: MutableList<OversendelseBarneverntjenesteType> = mutableListOf(),

    @field:XmlElement(name = "Relasjon")
    var relasjon: MutableList<RelasjonType> = mutableListOf(),
)
