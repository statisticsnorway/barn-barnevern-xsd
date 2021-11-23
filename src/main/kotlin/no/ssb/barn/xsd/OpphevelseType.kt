package no.ssb.barn.xsd

import jakarta.xml.bind.annotation.*

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OpphevelseType", propOrder = ["kode", "presisering"])
data class OpphevelseType(
        @field:XmlAttribute(name = "Kode", required = true)
        var kode: String,

        @field:XmlAttribute(name = "Presisering")
        var presisering: String? = null
)
