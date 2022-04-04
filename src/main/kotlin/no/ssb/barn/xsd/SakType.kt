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
    val migrertId: String?,

    @field:XmlAttribute(name = "StartDato", required = true)
    @field:XmlSchemaType(name = "date")
    var startDato: LocalDate,

    @field:XmlAttribute(name = "SluttDato")
    @field:XmlSchemaType(name = "date")
    var sluttDato: LocalDate?,

    @field:XmlAttribute(name = "Journalnummer", required = true)
    val journalnummer: String,

    @field:XmlAttribute(name = "Fodselsnummer", required = true)
    var fodselsnummer: String,

    @field:XmlAttribute(name = "DUFnummer")
    var duFnummer: String?,

    @field:XmlAttribute(name = "Fodseldato", required = true)
    @field:XmlSchemaType(name = "date")
    var fodseldato: LocalDate,

    @field:XmlAttribute(name = "Kjonn", required = true)
    val kjonn: String,

    @field:XmlAttribute(name = "Avsluttet")
    val avsluttet: Boolean?,

    @field:JacksonXmlProperty(localName = "Melding")
    @field:JacksonXmlElementWrapper(useWrapping = false)
    val melding: MutableList<MeldingType> = mutableListOf(),

    @field:JacksonXmlProperty(localName = "Undersokelse")
    @field:JacksonXmlElementWrapper(useWrapping = false)
    val undersokelse: MutableList<UndersokelseType> = mutableListOf(),

    @field:JacksonXmlProperty(localName = "Plan")
    @field:JacksonXmlElementWrapper(useWrapping = false)
    val plan: MutableList<PlanType> = mutableListOf(),

    @field:JacksonXmlProperty(localName = "Tiltak")
    @field:JacksonXmlElementWrapper(useWrapping = false)
    val tiltak: MutableList<TiltakType> = mutableListOf(),

    @field:JacksonXmlProperty(localName = "Vedtak")
    @field:JacksonXmlElementWrapper(useWrapping = false)
    val vedtak: MutableList<VedtakType> = mutableListOf(),

    @field:JacksonXmlProperty(localName = "Ettervern")
    @field:JacksonXmlElementWrapper(useWrapping = false)
    val ettervern: MutableList<EttervernType> = mutableListOf(),

    @field:JacksonXmlProperty(localName = "OversendelseBarneverntjeneste")
    @field:JacksonXmlElementWrapper(useWrapping = false)
    val oversendelseBarneverntjeneste: MutableList<OversendelseBarneverntjenesteType> = mutableListOf(),

    @field:JacksonXmlProperty(localName = "Flytting")
    @field:JacksonXmlElementWrapper(useWrapping = false)
    val flytting: MutableList<FlyttingType> = mutableListOf(),

    @field:JacksonXmlProperty(localName = "Relasjon")
    @field:JacksonXmlElementWrapper(useWrapping = false)
    val relasjon: MutableList<RelasjonType> = mutableListOf(),

    @field:JacksonXmlProperty(localName = "Slettet")
    @field:JacksonXmlElementWrapper(useWrapping = false)
    val slettet: MutableList<SlettetType> = mutableListOf()
)
