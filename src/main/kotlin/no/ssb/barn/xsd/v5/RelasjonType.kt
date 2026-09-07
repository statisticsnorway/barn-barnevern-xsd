package no.ssb.barn.xsd.v5

import jakarta.xml.bind.annotation.*
import java.util.UUID

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
    name = "RelasjonType", propOrder = ["id", "fraId", "fraKode", "tilId", "tilKode", "erSlettet"
    ]
)
data class RelasjonType(
    @field:XmlElement(name = "Id", required = true)
    val id: UUID,

    @field:XmlElement(name = "FraId", required = true)
    val fraId: String,

    @field:XmlElement(name = "FraKode", required = true)
    @XmlSchemaType(name = "string")
    val fraKode: BegrepKode,

    @field:XmlElement(name = "TilId", required = true)
    val tilId: UUID,

    @field:XmlElement(name = "TilKode", required = true)
    @XmlSchemaType(name = "string")
    val tilKode: BegrepKode,

    @field:XmlElement(name = "ErSlettet")
    val erSlettet: Boolean = false
)