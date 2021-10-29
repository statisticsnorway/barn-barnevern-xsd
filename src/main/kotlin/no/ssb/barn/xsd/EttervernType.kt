package no.ssb.barn.xsd

import javax.xml.bind.annotation.*
import javax.xml.datatype.XMLGregorianCalendar

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "EttervernType", propOrder = ["id", "tilbudSendtDato", "konklusjon"])
data class EttervernType(
        @XmlAttribute(name = "Id", required = true)
        var id: String,

        @XmlAttribute(name = "TilbudSendtDato", required = true)
        @XmlSchemaType(name = "date")
        var tilbudSendtDato: XMLGregorianCalendar,

        @XmlElement(name = "Konklusjon")
        var konklusjon: EttervernKonklusjonType? = null
)
