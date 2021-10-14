package no.ssb.barn.xsd

import javax.xml.bind.annotation.*
import javax.xml.datatype.XMLGregorianCalendar

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BarnevernType", propOrder = ["datoUttrekk", "fagsystem", "avgiver", "sak"])
data class BarnevernType(
        @XmlAttribute(name = "DatoUttrekk", required = true)
        @XmlSchemaType(name = "dateTime")
        var datoUttrekk: XMLGregorianCalendar,

        @XmlElement(name = "Fagsystem", required = true)
        var fagsystem: FagsystemType,

        @XmlElement(name = "Avgiver", required = true)
        var avgiver: AvgiverType,

        @XmlElement(name = "Sak", required = true)
        var sak: SakType
)