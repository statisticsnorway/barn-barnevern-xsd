package no.ssb.barn.xsd.v5

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper
import jakarta.xml.bind.annotation.*

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
    name = "LovhjemmelType", propOrder = ["lov", "kapittel", "paragraf", "ledd", "bokstav", "punktum"]
)
data class LovhjemmelType(
    @field:XmlElement(name = "Lov", required = true)
    @XmlSchemaType(name = "string")
    val lov: LovKode,

    @field:XmlElement(name = "Kapittel", required = true)
    val kapittel: String,

    @field:XmlElement(name = "Paragraf", required = true)
    val paragraf: String,

    @field:XmlElement(name = "Ledd")
    @field:JacksonXmlElementWrapper(useWrapping = false)
    val ledd: MutableList<String> = mutableListOf(),

    @field:XmlElement(name = "Bokstav")
    @field:JacksonXmlElementWrapper(useWrapping = false)
    val bokstav: MutableList<String> = mutableListOf(),

    @field:XmlElement(name = "Punktum")
    @field:JacksonXmlElementWrapper(useWrapping = false)
    val punktum: MutableList<String> = mutableListOf()
)