package no.ssb.barn.xsd

import javax.xml.bind.annotation.*

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OpphevelseType", propOrder = ["kode", "presisering"])
data class OpphevelseType(
        @XmlAttribute(name = "Kode", required = true)
        var kode: String,

        @XmlAttribute(name = "Presisering")
        var presisering: String? = null
)
