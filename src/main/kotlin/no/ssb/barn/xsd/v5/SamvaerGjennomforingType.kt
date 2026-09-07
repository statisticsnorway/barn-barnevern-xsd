package no.ssb.barn.xsd.v5

import jakarta.xml.bind.annotation.*
import java.time.LocalDate
import java.util.UUID

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
    name = "SamvaerGjennomforingType", propOrder = ["id", "part", "kode", "utfortDato"]
)
data class SamvaerGjennomforingType(
    @field:XmlElement(name = "Id", required = true)
    val id: UUID,

    @field:XmlElement(name = "Part", required = true)
    val part: String,

    @field:XmlElement(name = "Kode", required = true)
    val kode: String,

    @field:XmlElement(name = "UtfortDato", required = true)
    @XmlSchemaType(name = "date")
    val utfortDato: LocalDate
)