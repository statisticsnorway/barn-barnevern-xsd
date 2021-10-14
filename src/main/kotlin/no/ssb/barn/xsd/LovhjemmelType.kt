package no.ssb.barn.xsd

import javax.xml.bind.annotation.*

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "LovhjemmelType", propOrder = ["lov", "kapittel", "paragraf", "ledd", "punktum"])
data class LovhjemmelType(
        @XmlElement(name = "Lov", required = true)
        var lov: String,

        @XmlElement(name = "Kapittel", required = true)
        var kapittel: String,

        @XmlElement(name = "Paragraf", required = true)
        var paragraf: String,

        @XmlElement(name = "Ledd", required = true)
        var ledd: List<String>,

        @XmlElement(name = "Punktum")
        var punktum: List<String>? = null
)
