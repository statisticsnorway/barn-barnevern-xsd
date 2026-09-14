package no.ssb.barn.xsd.v5

import jakarta.xml.bind.annotation.*
import java.time.LocalDate
import java.util.UUID

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
    name = "BarnetsMedvirkningType", propOrder = ["id", "startDato", "kode"]
)
data class BarnetsMedvirkningType(
    @field:XmlElement(name = "Id", required = true)
    val id: UUID,

    @field:XmlElement(name = "StartDato", required = true)
    @XmlSchemaType(name = "date")
    val startDato: LocalDate,

    @field:XmlElement(name = "Kode", required = true)
    val kode: String
)