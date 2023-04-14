package no.ssb.barn.xsd

import java.time.LocalDate
import java.util.UUID
import jakarta.xml.bind.annotation.*

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TilsynAnsvarligType", propOrder = ["id", "startDato", "kommunenummer"])
data class TilsynAnsvarligType (
    @field:XmlAttribute(name = "Id", required = true)
    val id: UUID,

    @field:XmlAttribute(name = "StartDato", required = true)
    @field:XmlSchemaType(name = "date")
    val startDato: LocalDate,

    @field:XmlAttribute(name = "Kommunenummer", required = true)
    val kommunenummer: String
)