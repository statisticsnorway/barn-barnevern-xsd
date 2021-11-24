package no.ssb.barn.xsd

import jakarta.xml.bind.annotation.*
import no.ssb.barn.generator.RandomGenerator

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RelasjonType", propOrder = ["id", "fraId", "fraType", "tilId", "tilType"])
data class RelasjonType(
    @field:XmlAttribute(name = "Id", required = true)
    var id: String = RandomGenerator.generateRandomString(10),

    @field:XmlAttribute(name = "FraId", required = true)
    var fraId: String = RandomGenerator.generateRandomString(10),

    @field:XmlAttribute(name = "FraType", required = true)
    var fraType: BegrepsType = BegrepsType.MELDING,

    @field:XmlAttribute(name = "TilId", required = true)
    var tilId: String = RandomGenerator.generateRandomString(10),

    @field:XmlAttribute(name = "TilType", required = true)
    var tilType: BegrepsType = BegrepsType.UNDERSOKELSE
)
