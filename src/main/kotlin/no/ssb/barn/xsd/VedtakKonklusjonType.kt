package no.ssb.barn.xsd

import java.time.LocalDate
import javax.xml.bind.annotation.*

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "VedtakKonklusjon", propOrder = ["sluttDato"])
data class VedtakKonklusjonType(
    @field:XmlAttribute(name = "SluttDato", required = true)
    @field:XmlSchemaType(name = "date")
    val sluttDato: LocalDate
)
