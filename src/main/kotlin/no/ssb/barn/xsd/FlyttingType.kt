package no.ssb.barn.xsd

import java.time.LocalDate
import java.util.UUID
import jakarta.xml.bind.annotation.XmlAccessType
import jakarta.xml.bind.annotation.XmlAccessorType
import jakarta.xml.bind.annotation.XmlAttribute
import jakarta.xml.bind.annotation.XmlElement
import jakarta.xml.bind.annotation.XmlSchemaType
import jakarta.xml.bind.annotation.XmlType

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
    name = "FlyttingType",
    propOrder = ["id", "migrertId", "sluttDato", "arsakFra", "flytteTil"]
)
data class FlyttingType(
    @field:XmlAttribute(name = "Id", required = true)
    val id: UUID,

    @field:XmlAttribute(name = "MigrertId")
    val migrertId: String? = null,

    @field:XmlAttribute(name = "SluttDato", required = true)
    @field:XmlSchemaType(name = "date")
    val sluttDato: LocalDate,

    @field:XmlElement(name = "ArsakFra")
    val arsakFra: ArsakFraType,

    @field:XmlElement(name = "FlyttingTil")
    val flyttingTil: FlyttingTilType
)
