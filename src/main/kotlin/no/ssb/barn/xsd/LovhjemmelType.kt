package no.ssb.barn.xsd

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty
import jakarta.xml.bind.annotation.XmlAccessType
import jakarta.xml.bind.annotation.XmlAccessorType
import jakarta.xml.bind.annotation.XmlElement
import jakarta.xml.bind.annotation.XmlType

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "LovhjemmelType", propOrder = ["lov", "kapittel", "paragraf", "ledd", "bokstav", "punktum"])
data class LovhjemmelType(
    @field:XmlElement(name = "Lov", required = true)
    val lov: String,

    @field:XmlElement(name = "Kapittel", required = true)
    val kapittel: String,

    @field:XmlElement(name = "Paragraf", required = true)
    val paragraf: String,

    @field:JacksonXmlProperty(localName = "Ledd")
    @field:JacksonXmlElementWrapper(useWrapping = false)
    val ledd: MutableList<String> = mutableListOf(),

    @field:JacksonXmlProperty(localName = "Bokstav")
    @field:JacksonXmlElementWrapper(useWrapping = false)
    val bokstav: MutableList<String> = mutableListOf(),

    @field:JacksonXmlProperty(localName = "Punktum")
    @field:JacksonXmlElementWrapper(useWrapping = false)
    val punktum: MutableList<String> = mutableListOf(),
)
