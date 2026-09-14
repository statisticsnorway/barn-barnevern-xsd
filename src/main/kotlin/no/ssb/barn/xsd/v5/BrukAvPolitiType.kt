package no.ssb.barn.xsd.v5

import jakarta.xml.bind.annotation.*
import java.time.LocalDate
import java.util.UUID

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
    name = "BrukAvPolitiType", propOrder = ["id", "dato"]
)
data class BrukAvPolitiType(
    @field:XmlElement(name = "Id", required = true)
    val id: UUID,

    @field:XmlElement(name = "Dato", required = true)
    @XmlSchemaType(name = "date")
    val dato: LocalDate
)