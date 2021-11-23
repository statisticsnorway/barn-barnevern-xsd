package no.ssb.barn.xsd

import jakarta.xml.bind.annotation.*

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RelasjonType", propOrder = ["id", "fraId", "fraType", "tilId", "tilType"])
data class RelasjonType(
        @field:XmlAttribute(name = "Id", required = true)
        var id: String,

        @field:XmlAttribute(name = "FraId", required = true)
        var fraId:  String,

        @field:XmlAttribute(name = "FraType", required = true)
        var fraType: BegrepsType,

        @field:XmlAttribute(name = "TilId", required = true)
        var tilId:  String,

        @field:XmlAttribute(name = "TilType", required = true)
        var tilType: BegrepsType
)
