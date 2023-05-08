package no.ssb.barn.xsd

import jakarta.xml.bind.annotation.XmlAccessType
import jakarta.xml.bind.annotation.XmlAccessorType
import jakarta.xml.bind.annotation.XmlAttribute
import jakarta.xml.bind.annotation.XmlType
import java.util.UUID

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RelasjonType", propOrder = ["id", "fraId", "fraType", "tilId", "tilType"])
data class RelasjonType(
    @field:XmlAttribute(name = "Id", required = true)
    val id: UUID,

    /** fraId may contain a migrertId which is not UUID, hence type String */
    @field:XmlAttribute(name = "FraId", required = true)
    val fraId: String,

    @field:XmlAttribute(name = "FraType", required = true)
    val fraType: BegrepsType,

    @field:XmlAttribute(name = "TilId", required = true)
    val tilId: UUID,

    @field:XmlAttribute(name = "TilType", required = true)
    val tilType: BegrepsType
)
