package no.ssb.barn.xsd

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty
import java.time.LocalDate
import java.util.UUID
import jakarta.xml.bind.annotation.*

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OppfolgingType", propOrder = ["hyppighet", "utfort"])
data class OppfolgingType(
    @field:JacksonXmlProperty(localName = "Hyppighet")
    @field:JacksonXmlElementWrapper(useWrapping = false)
    val hyppighet: MutableList<OppfolgingHyppighetType> = mutableListOf(),

    @field:JacksonXmlProperty(localName = "Utfort")
    @field:JacksonXmlElementWrapper(useWrapping = false)
    val utfort: MutableList<OppfolgingUtfortType> = mutableListOf()
)
