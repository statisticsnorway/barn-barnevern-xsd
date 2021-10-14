package no.ssb.barn.xsd

import javax.xml.bind.annotation.*

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FagsystemType", propOrder = ["Leverandor", "Navn", "Versjon"])
data class FagsystemType (
    @XmlAttribute(name = "Leverandor", required = true)
    var leverandor: String,

    @XmlAttribute(name = "Navn", required = true)
    var navn: String,

    @XmlAttribute(name = "Versjon", required = true)
    var versjon: String
)
