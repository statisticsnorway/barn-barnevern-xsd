package no.ssb.barn.xsd

import javax.xml.bind.annotation.*

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Status", propOrder = ["endretDato", "kode"])
data class VedtakStatusType(
        @XmlAttribute(name = "EndretDato", required = true)
        @XmlSchemaType(name = "anySimpleType")
        var endretDato: String? = null,

        @XmlAttribute(name = "Kode", required = true)
        var kode: String? = null
)
