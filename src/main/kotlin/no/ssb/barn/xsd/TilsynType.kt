package no.ssb.barn.xsd

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty
import jakarta.xml.bind.annotation.XmlAccessType
import jakarta.xml.bind.annotation.XmlAccessorType
import jakarta.xml.bind.annotation.XmlType

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TilsynType", propOrder = ["ansvarlig", "hyppighet", "utfort"])
data class TilsynType(
    @field:JacksonXmlProperty(localName = "Ansvarlig")
    @field:JacksonXmlElementWrapper(useWrapping = false)
    val ansvarlig: MutableList<TilsynAnsvarligType> = mutableListOf(),

    @field:JacksonXmlProperty(localName = "Hyppighet")
    @field:JacksonXmlElementWrapper(useWrapping = false)
    val hyppighet: MutableList<TilsynHyppighetType> = mutableListOf(),

    @field:JacksonXmlProperty(localName = "Utfort")
    @field:JacksonXmlElementWrapper(useWrapping = false)
    val utfort: MutableList<TilsynUtfortType> = mutableListOf()
)
