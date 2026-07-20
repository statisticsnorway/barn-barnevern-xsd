package no.ssb.barn.xsd.v5

import jakarta.xml.bind.annotation.*
import java.time.LocalDate

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
    name = "UndersokelseKonklusjonType", propOrder = ["sluttDato", "kode"
    ]
)
data class UndersokelseKonklusjonType(
    @field:XmlElement(name = "SluttDato", required = true)
    @XmlSchemaType(name = "date")
    val sluttDato: LocalDate,

    @field:XmlElement(name = "Kode", required = true)
    val kode: String
)