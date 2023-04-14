package no.ssb.barn.xsd

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty
import java.time.LocalDate
import java.util.UUID
import jakarta.xml.bind.annotation.XmlAccessType
import jakarta.xml.bind.annotation.XmlAccessorType
import jakarta.xml.bind.annotation.XmlAttribute
import jakarta.xml.bind.annotation.XmlElement
import jakarta.xml.bind.annotation.XmlSchemaType
import jakarta.xml.bind.annotation.XmlType

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
    name = "PlanType",
    propOrder = ["id", "migrertId", "startDato", "evaluering", "konklusjon"]
)
data class PlanType(
    @field:XmlAttribute(name = "Id", required = true)
    val id: UUID,

    @field:XmlAttribute(name = "MigrertId")
    val migrertId: String? = null,

    @field:XmlAttribute(name = "StartDato", required = true)
    @field:XmlSchemaType(name = "date")
    val startDato: LocalDate,

    @field:XmlAttribute(name = "Plantype", required = true)
    val plantype: String,

    @field:JacksonXmlProperty(localName = "Evaluering")
    @field:JacksonXmlElementWrapper(useWrapping = false)
    val evaluering: MutableList<PlanEvalueringType> = mutableListOf(),

    @field:XmlElement(name = "Konklusjon")
    val konklusjon: PlanKonklusjonType? = null
)
