package no.ssb.barn.xsd.v5

import jakarta.xml.bind.annotation.*
import java.time.LocalDate

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
    name = "VedtakStatusType", propOrder = ["id", "endretDato", "kode"]
)
data class VedtakStatusType(
    @field:XmlElement(name = "Id", required = true)
    val id: String,

    @field:XmlElement(name = "EndretDato", required = true)
    @XmlSchemaType(name = "date")
    val endretDato: LocalDate,

    @field:XmlElement(name = "Kode", required = true)
    val kode: String
)