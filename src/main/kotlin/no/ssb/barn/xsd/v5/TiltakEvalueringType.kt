package no.ssb.barn.xsd.v5

import jakarta.xml.bind.annotation.*
import java.time.LocalDate
import java.util.UUID

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
    name = "TiltakEvalueringType", propOrder = ["id", "saksinnhold", "tiltaketKanAvsluttes", "gjennomfortDato"]
)
data class TiltakEvalueringType(
    @field:XmlElement(name = "Id", required = true)
    val id: UUID,

    @field:XmlElement(name = "Saksinnhold")
    val saksinnhold: MutableList<String> = mutableListOf(),

    @field:XmlElement(name = "TiltaketKanAvsluttes")
    val tiltaketKanAvsluttes: Boolean = false,

    @field:XmlElement(name = "GjennomfortDato", required = true)
    @XmlSchemaType(name = "date")
    val gjennomfortDato: LocalDate
)