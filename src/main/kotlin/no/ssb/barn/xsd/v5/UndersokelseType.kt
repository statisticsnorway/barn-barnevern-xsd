package no.ssb.barn.xsd.v5

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper
import jakarta.xml.bind.annotation.*
import java.time.LocalDate

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
    name = "UndersokelseType",
    propOrder = ["id", "migrertId", "startDato", "erSlettet", "vedtaksgrunnlag", "barnetsMedvirkning", "utvidetFrist", "konklusjon", "plan", "brukAvPoliti"
    ]
)
data class UndersokelseType(
    @field:XmlElement(name = "Id", required = true)
    val id: String,

    @field:XmlElement(name = "MigrertId")
    val migrertId: String? = null,

    @field:XmlElement(name = "StartDato", required = true)
    @XmlSchemaType(name = "date")
    val startDato: LocalDate,

    @field:XmlElement(name = "ErSlettet")
    val erSlettet: Boolean? = null,

    @field:XmlElement(name = "Vedtaksgrunnlag")
    @field:JacksonXmlElementWrapper(useWrapping = false)
    val vedtaksgrunnlag: MutableList<String> = mutableListOf(),

    @field:XmlElement(name = "BarnetsMedvirkning")
    @field:JacksonXmlElementWrapper(useWrapping = false)
    val barnetsMedvirkning: MutableList<BarnetsMedvirkningType> = mutableListOf(),

    @field:XmlElement(name = "UtvidetFrist")
    val utvidetFrist: UtvidetFristType? = null,

    @field:XmlElement(name = "Konklusjon")
    val konklusjon: UndersokelseKonklusjonType? = null,

    @field:XmlElement(name = "Plan")
    val plan: UndersokelsePlanType? = null,

    @field:XmlElement(name = "BrukAvPoliti")
    @field:JacksonXmlElementWrapper(useWrapping = false)
    val brukAvPoliti: MutableList<BrukAvPolitiType> = mutableListOf()
)