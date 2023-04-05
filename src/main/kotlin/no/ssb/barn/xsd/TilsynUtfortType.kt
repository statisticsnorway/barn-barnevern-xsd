package no.ssb.barn.xsd

import java.time.LocalDate
import java.util.*
import jakarta.xml.bind.annotation.*

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TilsynUtfortType", propOrder = ["id", "utfortDato"])
data class TilsynUtfortType (
    @field:XmlAttribute(name = "Id", required = true)
    val id: UUID,

    @field:XmlAttribute(name = "UtfortDato", required = true)
    @field:XmlSchemaType(name = "date")
    val utfortDato: LocalDate
)