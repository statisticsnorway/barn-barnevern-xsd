package no.ssb.barn.xsd.v5

import jakarta.xml.bind.annotation.*
import java.time.LocalDate

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
    name = "BrukAvPolitiType", propOrder = ["dato"
    ]
)
data class BrukAvPolitiType(
    @field:XmlElement(name = "Dato", required = true)
    @XmlSchemaType(name = "date")
    val dato: LocalDate? = null
)