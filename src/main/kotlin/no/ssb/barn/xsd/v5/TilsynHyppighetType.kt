package no.ssb.barn.xsd.v5

import jakarta.xml.bind.annotation.*
import java.time.LocalDate

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
    name = "TilsynHyppighetType", propOrder = ["startDato", "kode"
    ]
)
data class TilsynHyppighetType(
    @field:XmlElement(name = "StartDato", required = true)
    @XmlSchemaType(name = "date")
    val startDato: LocalDate,

    @field:XmlElement(name = "Kode", required = true)
    var kode: String
)