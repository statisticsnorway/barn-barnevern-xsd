package no.ssb.barn.xsd

import jakarta.xml.bind.annotation.*
import java.time.LocalDate
import java.util.UUID

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
    name = "FlyttingType",
    propOrder = ["id", "migrertId", "sluttDato", "erSlettet", "arsakFra", "flytteTil"]
)
data class FlyttingType(
    @field:XmlAttribute(name = "Id", required = true)
    val id: UUID,

    @field:XmlAttribute(name = "MigrertId")
    val migrertId: String? = null,

    @field:XmlAttribute(name = "SluttDato", required = true)
    @field:XmlSchemaType(name = "date")
    val sluttDato: LocalDate,

    @field:XmlAttribute(name = "ErSlettet")
    val erSlettet: Boolean = false,

    @field:XmlElement(name = "ArsakFra")
    val arsakFra: ArsakFraType,

    @field:XmlElement(name = "FlyttingTil")
    val flyttingTil: FlyttingTilType
)
