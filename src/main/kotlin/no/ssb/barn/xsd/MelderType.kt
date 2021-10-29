package no.ssb.barn.xsd

import javax.xml.bind.annotation.*

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MelderType", propOrder = ["kode", "presisering"])
data class MelderType(
        @XmlAttribute(name = "Kode", required = true)
        var kode: List<String?>,

        @XmlAttribute(name = "Presisering")
        var presisering: String? = null
)
