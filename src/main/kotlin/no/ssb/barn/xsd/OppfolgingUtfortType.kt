package no.ssb.barn.xsd

import jakarta.xml.bind.annotation.*
import java.time.LocalDate
import java.util.*

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OppfolgingUtfortType", propOrder = ["id", "utfortDato"])
data class OppfolgingUtfortType (
    @field:XmlAttribute(name = "Id", required = true)
    val id: UUID,

    @field:XmlAttribute(name = "UtfortDato", required = true)
    @field:XmlSchemaType(name = "date")
    val utfortDato: LocalDate
)