package no.ssb.barn.xsd

import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlAttribute
import javax.xml.bind.annotation.XmlType

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FagsystemType", propOrder = ["leverandor", "navn", "versjon"])
data class FagsystemType(
    @field:XmlAttribute(name = "Leverandor", required = true)
    val leverandor: String,

    @field:XmlAttribute(name = "Navn", required = true)
    val navn: String,

    @field:XmlAttribute(name = "Versjon", required = true)
    val versjon: String
)
