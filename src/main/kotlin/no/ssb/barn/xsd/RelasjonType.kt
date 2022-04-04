package no.ssb.barn.xsd

import java.util.*
import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlAttribute
import javax.xml.bind.annotation.XmlType

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RelasjonType", propOrder = ["id", "fraId", "fraType", "tilId", "tilType"])
data class RelasjonType(
    @field:XmlAttribute(name = "Id", required = true)
    val id: UUID,

    @field:XmlAttribute(name = "FraId", required = true)
    val fraId: UUID,

    @field:XmlAttribute(name = "FraType", required = true)
    val fraType: BegrepsType,

    @field:XmlAttribute(name = "TilId", required = true)
    val tilId: UUID,

    @field:XmlAttribute(name = "TilType", required = true)
    val tilType: BegrepsType
)
