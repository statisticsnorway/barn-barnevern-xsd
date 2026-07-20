package no.ssb.barn.xsd.v5

import jakarta.xml.bind.annotation.*
import java.time.LocalDate

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
    name = "SlettetType", propOrder = ["id", "kode", "slettetDato"
    ]
)
data class SlettetType(
    @field:XmlElement(name = "Id", required = true)
    var id: String,

    @field:XmlElement(name = "Kode", required = true)
    @XmlSchemaType(name = "string")
    var kode: BegrepKode,

    @field:XmlElement(name = "SlettetDato", required = true)
    @XmlSchemaType(name = "date")
    var slettetDato: LocalDate
)