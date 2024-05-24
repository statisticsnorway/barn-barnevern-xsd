package no.ssb.barn.xsd

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty
import jakarta.xml.bind.annotation.*
import java.time.LocalDate
import java.util.UUID

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
    name = "SakType",
    propOrder = ["id", "migrertId", "startDato", "sluttDato", "journalnummer", "avsluttet", "erSlettet",
        "personalia", "melding", "undersokelse", "plan", "tiltak", "vedtak", "ettervern",
        "oversendelseFylkesnemnd", "flytting", "relasjon", "slettet"]
)
data class SakTypeV3(
    @field:XmlAttribute(name = "Id", required = true)
    var id: UUID,

    @field:XmlAttribute(name = "MigrertId")
    val migrertId: String? = null,

    @field:XmlAttribute(name = "StartDato", required = true)
    @field:XmlSchemaType(name = "date")
    var startDato: LocalDate,

    @field:XmlAttribute(name = "SluttDato")
    @field:XmlSchemaType(name = "date")
    var sluttDato: LocalDate? = null,

    @field:XmlAttribute(name = "Journalnummer", required = true)
    val journalnummer: String,

    @field:XmlAttribute(name = "Avsluttet")
    val avsluttet: Boolean? = null,

    @field:XmlAttribute(name = "ErSlettet")
    val erSlettet: Boolean = false,

    @field:JacksonXmlProperty(localName = "Personalia")
    @field:JacksonXmlElementWrapper(useWrapping = false)
    val personalia: MutableList<PersonaliaType> = mutableListOf(),

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
    val tiltak: MutableList<TiltakTypeV3> = mutableListOf(),

    @field:JacksonXmlProperty(localName = "Vedtak")
    @field:JacksonXmlElementWrapper(useWrapping = false)
    val vedtak: MutableList<VedtakType> = mutableListOf(),

    @field:JacksonXmlProperty(localName = "Ettervern")
    @field:JacksonXmlElementWrapper(useWrapping = false)
    val ettervern: MutableList<EttervernType> = mutableListOf(),

    @field:JacksonXmlProperty(localName = "OversendelseFylkesnemnd")
    @field:JacksonXmlElementWrapper(useWrapping = false)
    val oversendelseFylkesnemnd: MutableList<OversendelseFylkesnemndType> = mutableListOf(),

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
