package no.ssb.barn.xsd

import javax.xml.bind.annotation.*
import javax.xml.datatype.XMLGregorianCalendar

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "UndersokelseType", propOrder = ["id", "migrertId", "startDato",
        "vedtaksgrunnlag", "utvidetFrist", "konklusjon"])
data class UndersokelseType(
        @XmlAttribute(name = "Id", required = true)
        var id: String,

        @XmlAttribute(name = "MigrertId")
        var migrertId: String? = null,

        @XmlAttribute(name = "StartDato", required = true)
        @XmlSchemaType(name = "date")
        var startDato: XMLGregorianCalendar,

        @XmlElement(name = "Vedtaksgrunnlag")
        var vedtaksgrunnlag: List<SaksinnholdType?>? = null,

        @XmlElement(name = "UtvidetFrist")
        var utvidetFrist: UndersokelseUtvidetFristType? = null,

        @XmlElement(name = "Konklusjon")
        var konklusjon: UndersokelseKonklusjonType? = null
)
