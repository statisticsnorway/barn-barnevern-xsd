package no.ssb.barn.xsd

import jakarta.xml.bind.annotation.*

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Evaluering", propOrder = ["utfortDato"])
data class PlanEvalueringType (
        @field:XmlAttribute(name = "UtfortDato", required = true)
        @field:XmlSchemaType(name = "anySimpleType")
        var utfortDato: String
)
