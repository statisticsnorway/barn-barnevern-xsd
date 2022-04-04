package no.ssb.barn.xsd

import java.time.LocalDate
import javax.xml.bind.annotation.*

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PlanKonklusjon", propOrder = ["sluttDato"])
data class PlanKonklusjonType(
    @field:XmlAttribute(name = "SluttDato", required = true)
    @field:XmlSchemaType(name = "date")
    val sluttDato: LocalDate
)
