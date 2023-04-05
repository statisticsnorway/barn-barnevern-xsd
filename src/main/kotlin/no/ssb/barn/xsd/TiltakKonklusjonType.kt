package no.ssb.barn.xsd

import java.time.LocalDate
import jakarta.xml.bind.annotation.*

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TiltakKonklusjon", propOrder = ["sluttDato"])
data class TiltakKonklusjonType(
    @field:XmlAttribute(name = "SluttDato", required = true)
    @field:XmlSchemaType(name = "date")
    val sluttDato: LocalDate
)