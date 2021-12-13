package no.ssb.barn.xsd

import jakarta.xml.bind.annotation.XmlAccessType
import jakarta.xml.bind.annotation.XmlAccessorType
import jakarta.xml.bind.annotation.XmlAttribute
import jakarta.xml.bind.annotation.XmlType
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter
import no.ssb.barn.converter.UuidAdapter
import no.ssb.barn.generator.RandomUtils
import java.util.*

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RelasjonType", propOrder = ["id", "fraId", "fraType", "tilId", "tilType"])
data class RelasjonType(
    @field:XmlAttribute(name = "Id", required = true)
    var id: String = RandomUtils.generateRandomString(10),

    @field:XmlAttribute(name = "FraId", required = true)
    @field:XmlJavaTypeAdapter(
        UuidAdapter::class
    )
    var fraId: UUID = UUID.randomUUID(),

    @field:XmlAttribute(name = "FraType", required = true)
    var fraType: BegrepsType = BegrepsType.MELDING,

    @field:XmlAttribute(name = "TilId", required = true)
    @field:XmlJavaTypeAdapter(
        UuidAdapter::class
    )
    var tilId: UUID = UUID.randomUUID(),

    @field:XmlAttribute(name = "TilType", required = true)
    var tilType: BegrepsType = BegrepsType.UNDERSOKELSE
)
