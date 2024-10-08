package no.ssb.barn.xsd

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty
import jakarta.xml.bind.annotation.*
import java.time.LocalDate
import java.util.UUID

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
    name = "MeldingType",
    propOrder = ["id", "migrertId", "startDato", "erSlettet", "melder", "saksinnhold", "konklusjon"]
)
data class MeldingType(
    @field:XmlAttribute(name = "Id", required = true)
    var id: UUID,

    @field:XmlAttribute(name = "MigrertId")
    val migrertId: String? = null,

    @field:XmlAttribute(name = "StartDato", required = true)
    @field:XmlSchemaType(name = "date")
    var startDato: LocalDate,

    @field:XmlAttribute(name = "ErSlettet")
    val erSlettet: Boolean = false,

    @field:JacksonXmlProperty(localName = "Melder")
    @field:JacksonXmlElementWrapper(useWrapping = false)
    val melder: MutableList<MelderType> = mutableListOf(),

    @field:JacksonXmlProperty(localName = "Saksinnhold")
    @field:JacksonXmlElementWrapper(useWrapping = false)
    val saksinnhold: MutableList<SaksinnholdType> = mutableListOf(),

    @field:XmlElement(name = "Konklusjon")
    var konklusjon: MeldingKonklusjonType? = null
)
