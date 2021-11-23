package no.ssb.barn.xsd

import jakarta.xml.bind.annotation.*

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FagsystemType", propOrder = ["leverandor", "navn", "versjon"])
data class FagsystemType (
    @field:XmlAttribute(name = "Leverandor", required = true)
    var leverandor: String = "",

    @field:XmlAttribute(name = "Navn", required = true)
    var navn: String = "",

    @field:XmlAttribute(name = "Versjon", required = true)
    var versjon: String = ""
)
