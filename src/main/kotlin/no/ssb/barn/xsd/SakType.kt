package no.ssb.barn.xsd

import javax.xml.bind.annotation.*
import javax.xml.datatype.XMLGregorianCalendar

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SakType", propOrder = ["id", "migrertId", "startDato", "sluttDato", "journalnummer", "fodselsnummer",
        "duFnummer", "avsluttet",
        "virksomhet"])
data class SakType(
        @XmlAttribute(name = "Id", required = true)
        var id: String,

        @XmlAttribute(name = "MigrertId")
        var migrertId: String? = null,

        @XmlAttribute(name = "StartDato", required = true)
        @XmlSchemaType(name = "date")
        var startDato: XMLGregorianCalendar,

        @XmlAttribute(name = "SluttDato")
        @XmlSchemaType(name = "date")
        var sluttDato: XMLGregorianCalendar? = null,

        @XmlAttribute(name = "Journalnummer", required = true)
        var journalnummer: String,

        @XmlAttribute(name = "Fodselsnummer")
        var fodselsnummer: String? = null,

        @XmlAttribute(name = "DUFnummer")
        var duFnummer: String? = null,

        @XmlAttribute(name = "Avsluttet")
        var avsluttet: Boolean? = null,

        @XmlElement(name = "Virksomhet", required = true)
        var virksomhet: List<VirksomhetType?>
)
