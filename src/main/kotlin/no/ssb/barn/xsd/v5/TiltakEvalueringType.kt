package no.ssb.barn.xsd.v5

import jakarta.xml.bind.annotation.*
import java.time.LocalDate

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
    name = "TiltakEvalueringType", propOrder = ["saksinnhold", "gjennomfortDato"
    ]
)
data class TiltakEvalueringType(
    @field:XmlElement(name = "Saksinnhold", required = true)
    val saksinnhold: String,

    @field:XmlElement(name = "GjennomfortDato", required = true)
    @XmlSchemaType(name = "date")
    val gjennomfortDato: LocalDate
)