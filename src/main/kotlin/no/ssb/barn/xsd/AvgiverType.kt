package no.ssb.barn.xsd

import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlAttribute
import javax.xml.bind.annotation.XmlType

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
    val bydelsnummer: String?,

    @field:XmlAttribute(name = "Bydelsnavn")
    val bydelsnavn: String?
)
