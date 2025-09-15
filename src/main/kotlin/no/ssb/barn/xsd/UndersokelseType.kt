package no.ssb.barn.xsd

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty
import jakarta.xml.bind.annotation.*
import java.time.LocalDate
import java.util.UUID

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
    name = "UndersokelseType",
    propOrder = ["id", "migrertId", "startDato", "erSlettet", "vedtaksgrunnlag", "utvidetFrist", "barnetsMedvirkning", "konklusjon"]
)
data class UndersokelseType(
    @field:XmlAttribute(name = "Id", required = true)
    var id: UUID,

    @field:XmlAttribute(name = "MigrertId")
    val migrertId: String? = null,

    @field:XmlAttribute(name = "StartDato", required = true)
    @field:XmlSchemaType(name = "date")
    var startDato: LocalDate,

    @field:XmlAttribute(name = "ErSlettet")
    val erSlettet: Boolean = false,

    @field:JacksonXmlProperty(localName = "Vedtaksgrunnlag")
    @field:JacksonXmlElementWrapper(useWrapping = false)
    val vedtaksgrunnlag: MutableList<SaksinnholdType> = mutableListOf(),

    @field:XmlElement(name = "BarnetsMedvirkning")
    var barnetsMedvirkning: BarnetsMedvirkningType? = null,

    @field:XmlElement(name = "UtvidetFrist")
    var utvidetFrist: UndersokelseUtvidetFristType? = null,

    @field:XmlElement(name = "Konklusjon")
    var konklusjon: UndersokelseKonklusjonType? = null
)
