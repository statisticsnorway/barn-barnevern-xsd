package no.ssb.barn.xsd

import no.ssb.barn.converter.UuidAdapter
import java.util.*
import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlAttribute
import javax.xml.bind.annotation.XmlType
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RelasjonType", propOrder = ["id", "fraId", "fraType", "tilId", "tilType"])
data class RelasjonType(
    @field:XmlAttribute(name = "Id", required = true)
    @field:XmlJavaTypeAdapter(
        UuidAdapter::class
    )
    var id: UUID? = null,

    @field:XmlAttribute(name = "FraId", required = true)
    @field:XmlJavaTypeAdapter(
        UuidAdapter::class
    )
    var fraId: UUID? = null,

    @field:XmlAttribute(name = "FraType", required = true)
    var fraType: BegrepsType = BegrepsType.MELDING,

    @field:XmlAttribute(name = "TilId", required = true)
    @field:XmlJavaTypeAdapter(
        UuidAdapter::class
    )
    var tilId: UUID? = null,

    @field:XmlAttribute(name = "TilType", required = true)
    var tilType: BegrepsType = BegrepsType.UNDERSOKELSE
)
