package no.ssb.barn.xsd

import javax.xml.bind.annotation.*
import javax.xml.datatype.XMLGregorianCalendar

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FlyttingType", propOrder = ["id", "sluttDato", "kode"])
data class FlyttingType(
        @XmlAttribute(name = "Id", required = true)
        var id: String? = null,

        @XmlAttribute(name = "SluttDato", required = true)
        @XmlSchemaType(name = "date")
        var sluttDato: XMLGregorianCalendar? = null,

        @XmlAttribute(name = "Kode", required = true)
        var kode: String? = null
)
