package no.ssb.barn.xsd

import java.time.LocalDate
import java.util.*
import jakarta.xml.bind.annotation.*

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OversendelsePrivatKravType", propOrder = ["id", "startDato", "konklusjon"])
data class OversendelsePrivatKravType(
    @field:XmlAttribute(name = "Id", required = true)
    val id: UUID,

    @field:XmlAttribute(name = "StartDato", required = true)
    @field:XmlSchemaType(name = "date")
    val startDato: LocalDate,

    @field:XmlElement(name = "Konklusjon")
    val konklusjon: OversendelsePrivatKravKonklusjonType? = null
)
