package no.ssb.barn.xsd

import javax.xml.bind.annotation.*
import javax.xml.datatype.XMLGregorianCalendar

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TilsynType", propOrder = ["id", "utfortDato"])
data class TilsynType(
        @XmlAttribute(name = "Id", required = true)
        var id: String,

        @XmlAttribute(name = "UtfortDato", required = true)
        @XmlSchemaType(name = "date")
        var utfortDato: XMLGregorianCalendar
)
