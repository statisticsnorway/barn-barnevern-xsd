package no.ssb.barn.xsd

import javax.xml.bind.annotation.*
import javax.xml.datatype.XMLGregorianCalendar

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MeldingType", propOrder = ["id", "migrertId", "startDato", "melder", "saksinnhold", "konklusjon"])
data class MeldingType(
        @XmlAttribute(name = "Id", required = true)
        var id: String,

        @XmlAttribute(name = "MigrertId")
        var migrertId: String? = null,

        @XmlAttribute(name = "StartDato", required = true)
        @XmlSchemaType(name = "date")
        var startDato: XMLGregorianCalendar,

        @XmlElement(name = "Melder")
        var melder: List<MelderType?>? = null,

        @XmlElement(name = "Saksinnhold")
        var saksinnhold: List<SaksinnholdType>? = null,

        @XmlElement(name = "Konklusjon")
        var konklusjon: MeldingKonklusjonType? = null
)
