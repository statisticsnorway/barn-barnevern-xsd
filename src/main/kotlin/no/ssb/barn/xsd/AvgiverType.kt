package no.ssb.barn.xsd

import javax.xml.bind.annotation.*

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AvgiverType", propOrder = ["organisasjonsnummer", "kommunenummer", "kommunenavn"])
data class AvgiverType (
    @XmlAttribute(name = "Organisasjonsnummer", required = true)
    var organisasjonsnummer: String,

    @XmlAttribute(name = "Kommunenummer", required = true)
    var kommunenummer: String,

    @XmlAttribute(name = "Kommunenavn", required = true)
    var kommunenavn: String
)
