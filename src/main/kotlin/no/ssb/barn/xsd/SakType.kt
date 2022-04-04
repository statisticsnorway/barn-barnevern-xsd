package no.ssb.barn.xsd

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty
import java.time.LocalDate
import java.util.*
import javax.xml.bind.annotation.*

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
    name = "SakType",
    propOrder = ["id", "migrertId", "startDato", "sluttDato",
        "journalnummer", "fodselsnummer", "duFnummer", "fodseldato", "kjonn",
        "avsluttet", "melding", "undersokelse", "plan", "tiltak", "vedtak", "ettervern", "oversendelseBarneverntjeneste",
        "flytting", "relasjon", "slettet"]
)
data class SakType(
    @field:XmlAttribute(name = "Id", required = true)
    var id: UUID,

    @field:XmlAttribute(name = "MigrertId")
    var migrertId: String?,

    @field:XmlAttribute(name = "StartDato", required = true)
    @field:XmlSchemaType(name = "date")
    var startDato: LocalDate,

    @field:XmlAttribute(name = "SluttDato")
    @field:XmlSchemaType(name = "date")
    var sluttDato: LocalDate?,

    @field:XmlAttribute(name = "Journalnummer", required = true)
    var journalnummer: String,

    @field:XmlAttribute(name = "Fodselsnummer", required = true)
    var fodselsnummer: String,

    @field:XmlAttribute(name = "DUFnummer")
    var duFnummer: String?,

    @field:XmlAttribute(name = "Fodseldato", required = true)
    @field:XmlSchemaType(name = "date")
    var fodseldato: LocalDate,

    @field:XmlAttribute(name = "Kjonn", required = true)
    var kjonn: String,

    @field:XmlAttribute(name = "Avsluttet")
    var avsluttet: Boolean?,

    @field:JacksonXmlProperty(localName = "Melding")
    @field:JacksonXmlElementWrapper(useWrapping = false)
    var melding: MutableList<MeldingType> = mutableListOf(),

    @field:JacksonXmlProperty(localName = "Undersokelse")
    @field:JacksonXmlElementWrapper(useWrapping = false)
    var undersokelse: MutableList<UndersokelseType> = mutableListOf(),

    @field:JacksonXmlProperty(localName = "Plan")
    @field:JacksonXmlElementWrapper(useWrapping = false)
    var plan: MutableList<PlanType> = mutableListOf(),

    @field:JacksonXmlProperty(localName = "Tiltak")
    @field:JacksonXmlElementWrapper(useWrapping = false)
    var tiltak: MutableList<TiltakType> = mutableListOf(),

    @field:JacksonXmlProperty(localName = "Vedtak")
    @field:JacksonXmlElementWrapper(useWrapping = false)
    var vedtak: MutableList<VedtakType> = mutableListOf(),

    @field:JacksonXmlProperty(localName = "Ettervern")
    @field:JacksonXmlElementWrapper(useWrapping = false)
    var ettervern: MutableList<EttervernType> = mutableListOf(),

    @field:JacksonXmlProperty(localName = "OversendelseBarneverntjeneste")
    @field:JacksonXmlElementWrapper(useWrapping = false)
    var oversendelseBarneverntjeneste: MutableList<OversendelseBarneverntjenesteType> = mutableListOf(),

    @field:JacksonXmlProperty(localName = "Flytting")
    @field:JacksonXmlElementWrapper(useWrapping = false)
    var flytting: MutableList<FlyttingType> = mutableListOf(),

    @field:JacksonXmlProperty(localName = "Relasjon")
    @field:JacksonXmlElementWrapper(useWrapping = false)
    var relasjon: MutableList<RelasjonType> = mutableListOf(),

    @field:JacksonXmlProperty(localName = "Slettet")
    @field:JacksonXmlElementWrapper(useWrapping = false)
    var slettet: MutableList<SlettetType> = mutableListOf()
)
