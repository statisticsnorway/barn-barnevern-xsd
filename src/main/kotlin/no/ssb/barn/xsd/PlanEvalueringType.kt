package no.ssb.barn.xsd

import javax.xml.bind.annotation.*

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Evaluering", propOrder = ["utfortDato"])
data class PlanEvalueringType (
        @XmlAttribute(name = "UtfortDato", required = true)
        @XmlSchemaType(name = "anySimpleType")
        var utfortDato: String
)
