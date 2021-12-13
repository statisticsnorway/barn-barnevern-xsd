package no.ssb.barn.xsd

import javax.xml.bind.annotation.*
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter
import no.ssb.barn.converter.LocalDateAdapter
import java.time.LocalDate

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Evaluering", propOrder = ["utfortDato"])
data class PlanEvalueringType (
        @field:XmlAttribute(name = "UtfortDato", required = true)
        @field:XmlSchemaType(name = "date")
        @field:XmlJavaTypeAdapter(
                LocalDateAdapter::class
        )
        var utfortDato: LocalDate = LocalDate.now()
)
