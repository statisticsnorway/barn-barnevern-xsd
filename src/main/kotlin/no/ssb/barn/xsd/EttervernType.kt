package no.ssb.barn.xsd

import java.time.LocalDate
import java.util.*
import javax.xml.bind.annotation.*

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "EttervernType", propOrder = ["id", "tilbudSendtDato", "konklusjon"])
data class EttervernType(
    @field:XmlAttribute(name = "Id", required = true)
    val id: UUID,

    @field:XmlAttribute(name = "TilbudSendtDato", required = true)
    @field:XmlSchemaType(name = "date")
    val tilbudSendtDato: LocalDate,

    @field:XmlElement(name = "Konklusjon")
    val konklusjon: EttervernKonklusjonType? = null
)
