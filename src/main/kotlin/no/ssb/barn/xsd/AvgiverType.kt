package no.ssb.barn.xsd

import jakarta.xml.bind.annotation.XmlAccessType
import jakarta.xml.bind.annotation.XmlAccessorType
import jakarta.xml.bind.annotation.XmlAttribute
import jakarta.xml.bind.annotation.XmlType

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
    name = "AvgiverType",
    propOrder = ["organisasjonsnummer", "kommunenummer", "kommunenavn", "bydelsnummer", "bydelsnavn"]
)
data class AvgiverType(
    @field:XmlAttribute(name = "Organisasjonsnummer", required = true)
    val organisasjonsnummer: String,

    @field:XmlAttribute(name = "Kommunenummer", required = true)
    val kommunenummer: String,

    @field:XmlAttribute(name = "Kommunenavn", required = true)
    val kommunenavn: String,

    @field:XmlAttribute(name = "Bydelsnummer")
    val bydelsnummer: String? = null,

    @field:XmlAttribute(name = "Bydelsnavn")
    val bydelsnavn: String? = null
)
