package no.ssb.barn.xsd.v5

import jakarta.xml.bind.annotation.*
import java.time.LocalDate
import java.util.UUID

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
    name = "TilsynAnsvarligType", propOrder = ["id", "startDato", "kommunenummer"
    ]
)
data class TilsynAnsvarligType(
    @field:XmlElement(name = "Id", required = true)
    val id: UUID,

    @field:XmlElement(name = "StartDato", required = true)
    @XmlSchemaType(name = "date")
    val startDato: LocalDate,

    @field:XmlElement(name = "Kommunenummer", required = true)
    val kommunenummer: String
)