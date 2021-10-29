package no.ssb.barn.xsd

import javax.xml.bind.annotation.*
import javax.xml.datatype.XMLGregorianCalendar

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Konklusjon", propOrder = ["sluttDato"])
data class TiltakKonklusjonType (
        @XmlAttribute(name = "SluttDato", required = true)
        @XmlSchemaType(name = "date")
        var sluttDato: XMLGregorianCalendar
)
