package no.ssb.barn.xsd

import javax.xml.bind.annotation.*

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RelasjonType", propOrder = ["id", "fraId", "fraType", "tilId", "tilType"])
data class RelasjonType(
        @XmlAttribute(name = "Id", required = true)
        var id: String,

        @XmlAttribute(name = "FraId", required = true)
        var fraId:  String,

        @XmlAttribute(name = "FraType", required = true)
        var fraType: BegrepsType,

        @XmlAttribute(name = "TilId", required = true)
        var tilId:  String,

        @XmlAttribute(name = "TilType", required = true)
        var tilType: BegrepsType
)
