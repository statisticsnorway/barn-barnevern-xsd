package no.ssb.barn.xsd.v5

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper
import jakarta.xml.bind.annotation.*
import java.time.LocalDate
import java.util.UUID

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
    name = "SakType",
    propOrder = ["id", "migrertId", "startDato", "sluttDato", "journalnummer", "avsluttet", "erSlettet", "brukAvPoliti", "datoIndividuellPlan", "personalia", "henvendelse", "undersokelse", "plan", "tiltak", "vedtak", "oversendelseFylkesnemnd", "flytting", "relasjon", "slettet"
    ]
)
data class SakType(
    @field:XmlElement(required = true)
    var id: UUID,

    @field:XmlElement(name = "MigrertId")
    var migrertId: String? = null,

    @field:XmlElement(name = "StartDato", required = true)
    @XmlSchemaType(name = "date")
    var startDato: LocalDate,

    @field:XmlElement(name = "SluttDato")
    @XmlSchemaType(name = "date")
    var sluttDato: LocalDate? = null,

    @field:XmlElement(name = "Journalnummer", required = true)
    var journalnummer: String,

    @field:XmlElement(name = "Avsluttet")
    var avsluttet: Boolean? = null,

    @field:XmlElement(name = "ErSlettet")
    var erSlettet: Boolean = false,

    @field:XmlElement(name = "BrukAvPoliti")
    @field:JacksonXmlElementWrapper(useWrapping = false)
    val brukAvPoliti: MutableList<BrukAvPolitiType> = mutableListOf(),

    @field:XmlElement(name = "DatoIndividuellPlan")
    @field:JacksonXmlElementWrapper(useWrapping = false)
    @XmlSchemaType(name = "date")
    var datoIndividuellPlan: MutableList<LocalDate> = mutableListOf(),

    @field:XmlElement(name = "Personalia")
    @field:JacksonXmlElementWrapper(useWrapping = false)
    var personalia: MutableList<PersonaliaType> = mutableListOf(),

    @field:XmlElement(name = "Henvendelse")
    @field:JacksonXmlElementWrapper(useWrapping = false)
    var henvendelse: MutableList<HenvendelseType> = mutableListOf(),

    @field:XmlElement(name = "Undersokelse")
    @field:JacksonXmlElementWrapper(useWrapping = false)
    var undersokelse: MutableList<UndersokelseType> = mutableListOf(),

    @field:XmlElement(name = "Plan")
    @field:JacksonXmlElementWrapper(useWrapping = false)
    var plan: MutableList<PlanType> = mutableListOf(),

    @field:XmlElement(name = "Tiltak")
    @field:JacksonXmlElementWrapper(useWrapping = false)
    var tiltak: MutableList<TiltakType> = mutableListOf(),

    @field:XmlElement(name = "Vedtak")
    @field:JacksonXmlElementWrapper(useWrapping = false)
    var vedtak: MutableList<VedtakType> = mutableListOf(),

    @field:XmlElement(name = "OversendelseFylkesnemnd")
    @field:JacksonXmlElementWrapper(useWrapping = false)
    var oversendelseFylkesnemnd: MutableList<OversendelseBarneverntjenesteType> = mutableListOf(),

    @field:XmlElement(name = "Flytting")
    @field:JacksonXmlElementWrapper(useWrapping = false)
    var flytting: MutableList<FlyttingType> = mutableListOf(),

    @field:XmlElement(name = "Relasjon")
    @field:JacksonXmlElementWrapper(useWrapping = false)
    var relasjon: MutableList<RelasjonType> = mutableListOf(),

    @field:XmlElement(name = "Slettet")
    @field:JacksonXmlElementWrapper(useWrapping = false)
    var slettet: MutableList<SlettetType> = mutableListOf()
)