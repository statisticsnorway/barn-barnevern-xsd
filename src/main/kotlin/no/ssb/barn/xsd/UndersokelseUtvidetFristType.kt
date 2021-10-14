package no.ssb.barn.xsd

import javax.xml.bind.annotation.*
import javax.xml.datatype.XMLGregorianCalendar

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "UtvidetFrist", propOrder = ["startDato", "innvilget"])
data class UndersokelseUtvidetFristType(
        @XmlAttribute(name = "StartDato", required = true) @XmlSchemaType(name = "date")
        var startDato: XMLGregorianCalendar,

        @XmlAttribute(name = "Innvilget")
        var innvilget: String? = null
)
