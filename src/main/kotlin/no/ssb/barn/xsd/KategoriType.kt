package no.ssb.barn.xsd

import javax.xml.bind.annotation.*

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "KategoriType", propOrder = ["kode", "presisering"])
data class KategoriType(
        @XmlAttribute(name = "Kode", required = true)
        var kode: String,

        @XmlAttribute(name = "Presisering")
        var presisering: String? = null
)
