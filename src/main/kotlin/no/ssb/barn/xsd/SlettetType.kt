package no.ssb.barn.xsd

import java.time.LocalDate
import java.util.*
import javax.xml.bind.annotation.*

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
    name = "SlettetType",
    propOrder = ["id", "type", "sluttDato"]
)
data class SlettetType(
    @field:XmlAttribute(name = "Id", required = true)
    val id: UUID,

    @field:XmlAttribute(name = "Type", required = true)
    val type: BegrepsType,

    @field:XmlAttribute(name = "SluttDato", required = true)
    @field:XmlSchemaType(name = "date")
    val sluttDato: LocalDate
)