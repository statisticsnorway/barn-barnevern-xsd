package no.ssb.barn.xsd

import javax.xml.bind.annotation.*
import javax.xml.datatype.XMLGregorianCalendar

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OversendelseBarneverntjenesteType", propOrder = ["id", "startDato", "lovhjemmel", "jmfrLovhjemmel"])
data class OversendelseBarneverntjenesteType(
        @XmlAttribute(name = "Id", required = true)
        var id: String,

        @XmlAttribute(name = "StartDato", required = true)
        @XmlSchemaType(name = "date")
        var startDato: XMLGregorianCalendar,

        @XmlElement(name = "Lovhjemmel", required = true)
        var lovhjemmel: LovhjemmelType,

        @XmlElement(name = "JmfrLovhjemmel")
        var jmfrLovhjemmel: List<LovhjemmelType>? = null
)
