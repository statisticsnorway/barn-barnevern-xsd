package no.ssb.barn.xsd.v5

import jakarta.xml.bind.annotation.XmlAccessType
import jakarta.xml.bind.annotation.XmlAccessorType
import jakarta.xml.bind.annotation.XmlElement
import jakarta.xml.bind.annotation.XmlType

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
    name = "AvgiverType",
    propOrder = ["organisasjonsnummer", "kommunenummer", "kommunenavn", "bydelsnummer", "bydelsnavn"]
)
data class AvgiverType(
    @field:XmlElement(name = "Organisasjonsnummer", required = true)
    val organisasjonsnummer: String,

    @field:XmlElement(name = "Kommunenummer", required = true)
    val kommunenummer: String,

    @field:XmlElement(name = "Kommunenavn", required = true)
    val kommunenavn: String,

    @field:XmlElement(name = "Bydelsnummer")
    val bydelsnummer: String? = null,

    @field:XmlElement(name = "Bydelsnavn")
    val bydelsnavn: String? = null
)