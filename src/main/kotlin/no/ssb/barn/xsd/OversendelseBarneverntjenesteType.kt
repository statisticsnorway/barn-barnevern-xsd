package no.ssb.barn.xsd

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty
import java.time.LocalDate
import java.util.*
import javax.xml.bind.annotation.*

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OversendelseBarneverntjenesteType", propOrder = ["id", "startDato", "lovhjemmel", "jmfrLovhjemmel"])
data class OversendelseBarneverntjenesteType(
    @field:XmlAttribute(name = "Id", required = true)
    val id: UUID,

    @field:XmlAttribute(name = "StartDato", required = true)
    @field:XmlSchemaType(name = "date")
    val startDato: LocalDate,

    @field:XmlElement(name = "Lovhjemmel", required = true)
    val lovhjemmel: LovhjemmelType,

    @field:JacksonXmlProperty(localName = "JmfrLovhjemmel")
    @field:JacksonXmlElementWrapper(useWrapping = false)
    val jmfrLovhjemmel: MutableList<LovhjemmelType> = mutableListOf()
)
