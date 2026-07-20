package no.ssb.barn.xsd.v5

import jakarta.xml.bind.annotation.*
import java.time.LocalDate

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
    name = "UtvidetFristType", propOrder = ["startDato", "innvilget"
    ]
)
data class UtvidetFristType (
    @field:XmlElement(name = "StartDato", required = true)
    @XmlSchemaType(name = "date")
    val startDato: LocalDate,

    @field:XmlElement(name = "Innvilget")
    val isInnvilget: Boolean? = null
)