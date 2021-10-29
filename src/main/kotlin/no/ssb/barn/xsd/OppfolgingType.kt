package no.ssb.barn.xsd

import javax.xml.bind.annotation.*
import javax.xml.datatype.XMLGregorianCalendar

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OppfolgingType", propOrder = ["id", "utfortDato"])
data class OppfolgingType (
    @XmlAttribute(name = "Id", required = true)
    var id: String,

    @XmlAttribute(name = "UtfortDato", required = true)
    @XmlSchemaType(name = "date")
    var utfortDato: XMLGregorianCalendar
)
