package no.ssb.barn.xsd.v5

import jakarta.xml.bind.annotation.*
import java.time.LocalDate

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
    name = "TiltakKonklusjonType", propOrder = ["sluttDato", "opphevelse", "avslutningAvTiltak"
    ]
)
data class TiltakKonklusjonType(
    @field:XmlElement(name = "SluttDato", required = true)
    @XmlSchemaType(name = "date")
    val sluttDato: LocalDate,

    @field:XmlElement(name = "Opphevelse")
    val opphevelse: String? = null,

    @field:XmlElement(name = "AvslutningAvTiltak")
    val avslutningAvTiltak: String? = null
)