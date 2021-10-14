package no.ssb.barn.xsd

import javax.xml.bind.annotation.*
import javax.xml.datatype.XMLGregorianCalendar

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PlanType", propOrder = ["id", "migrertId", "startDato", "evaluering", "konklusjon"])
data class PlanType(
        @XmlAttribute(name = "Id", required = true)
        var id: String,

        @XmlAttribute(name = "MigrertId")
        var migrertId: String? = null,

        @XmlAttribute(name = "StartDato", required = true)
        @XmlSchemaType(name = "date")
        var startDato: XMLGregorianCalendar,

        @XmlAttribute(name = "Plantype", required = true)
        var plantype: String,

        @XmlElement(name = "Evaluering")
        var evaluering: List<PlanEvalueringType?>? = null,

        @XmlElement(name = "Konklusjon")
        var konklusjon: PlanKonklusjonType? = null
)
