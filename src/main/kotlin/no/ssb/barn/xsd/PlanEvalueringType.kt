package no.ssb.barn.xsd

import no.ssb.barn.converter.LocalDateTimeAdapter
import java.time.LocalDateTime
import javax.xml.bind.annotation.*
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Evaluering", propOrder = ["utfortDato"])
data class PlanEvalueringType (
        @field:XmlAttribute(name = "UtfortDato", required = true)
        @field:XmlSchemaType(name = "date")
        @field:XmlJavaTypeAdapter(
                LocalDateTimeAdapter::class
        )
        var utfortDato: LocalDateTime = LocalDateTime.now()
)
