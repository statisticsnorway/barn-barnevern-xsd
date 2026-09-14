package no.ssb.barn.xsd.v5

import jakarta.xml.bind.annotation.XmlAccessType
import jakarta.xml.bind.annotation.XmlAccessorType
import jakarta.xml.bind.annotation.XmlElement
import jakarta.xml.bind.annotation.XmlType

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
    name = "FagsystemType", propOrder = ["leverandor", "navn", "versjon"
    ]
)
data class FagsystemType(
    @field:XmlElement(name = "Leverandor", required = true)
    val leverandor: String,

    @field:XmlElement(name = "Navn", required = true)
    val navn: String,

    @field:XmlElement(name = "Versjon", required = true)
    val versjon: String
)