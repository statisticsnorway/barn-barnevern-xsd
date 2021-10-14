package no.ssb.barn.xsd

import javax.xml.bind.annotation.*
import javax.xml.datatype.XMLGregorianCalendar

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OversendelsePrivatKravType", propOrder = ["id", "startDato", "konklusjon"])
data class OversendelsePrivatKravType(
        @XmlAttribute(name = "Id", required = true)
        var id: String,

        @XmlAttribute(name = "StartDato", required = true)
        @XmlSchemaType(name = "date")
        var startDato: XMLGregorianCalendar,

        @XmlElement(name = "Konklusjon")
        var konklusjon: OversendelsePrivatKravKonklusjonType? = null
)
