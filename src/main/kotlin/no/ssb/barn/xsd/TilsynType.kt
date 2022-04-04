package no.ssb.barn.xsd

import java.time.LocalDate
import java.util.*
import javax.xml.bind.annotation.*

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TilsynType", propOrder = ["id", "utfortDato"])
data class TilsynType(
    @field:XmlAttribute(name = "Id", required = true)
    val id: UUID,

    @field:XmlAttribute(name = "UtfortDato", required = true)
    @field:XmlSchemaType(name = "date")
    val utfortDato: LocalDate
)
