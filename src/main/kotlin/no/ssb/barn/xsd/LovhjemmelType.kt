package no.ssb.barn.xsd

import jakarta.xml.bind.annotation.*

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "LovhjemmelType", propOrder = ["lov", "kapittel", "paragraf", "ledd", "punktum"])
data class LovhjemmelType(
        @field:XmlElement(name = "Lov", required = true)
        var lov: String,

        @field:XmlElement(name = "Kapittel", required = true)
        var kapittel: String,

        @field:XmlElement(name = "Paragraf", required = true)
        var paragraf: String,

        @field:XmlElement(name = "Ledd", required = true)
        var ledd: MutableList<String>,

        @field:XmlElement(name = "Punktum")
        var punktum: MutableList<String>? = null
)
