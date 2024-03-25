package no.ssb.barn.xsd

import jakarta.xml.bind.annotation.*
import java.time.LocalDate
import java.util.*

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OppfolgingHyppighetType", propOrder = ["id", "startDato", "kode"])
data class OppfolgingHyppighetType(
    @field:XmlAttribute(name = "Id", required = true)
    val id: UUID,

    @field:XmlAttribute(name = "StartDato", required = true)
    @field:XmlSchemaType(name = "date")
    val startDato: LocalDate,

    @field:XmlAttribute(name = "Kode", required = true)
    val kode: String
)
