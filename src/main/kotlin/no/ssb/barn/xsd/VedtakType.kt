package no.ssb.barn.xsd

import javax.xml.bind.annotation.*
import javax.xml.datatype.XMLGregorianCalendar

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "VedtakType", propOrder = ["id", "migrertId", "startDato",
        "lovhjemmel", "jmfrLovhjemmel", "krav", "status", "konklusjon"])
data class VedtakType(
        @XmlAttribute(name = "Id", required = true)
        var id: String,

        @XmlAttribute(name = "StartDato", required = true)
        @XmlSchemaType(name = "date")
        var startDato: XMLGregorianCalendar,

        @XmlElement(name = "Lovhjemmel", required = true)
        var lovhjemmel: LovhjemmelType,

        @XmlElement(name = "JmfrLovhjemmel")
        var jmfrLovhjemmel: List<LovhjemmelType>? = null,

        @XmlElement(name = "Krav")
        var krav: List<OversendelsePrivatKravType>? = null,

        @XmlElement(name = "Status")
        var status: List<VedtakStatusType>? = null,

        @XmlElement(name = "Konklusjon")
        var konklusjon: VedtakKonklusjonType? = null
)
