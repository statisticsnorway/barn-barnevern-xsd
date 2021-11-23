package no.ssb.barn.xsd

import no.ssb.barn.converter.LocalDateAdapter
import java.time.LocalDate
import javax.xml.bind.annotation.*
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
    name = "VedtakType", propOrder = ["id", "migrertId", "startDato",
        "lovhjemmel", "jmfrLovhjemmel", "krav", "status", "konklusjon"]
)
data class VedtakType(
    @field:XmlAttribute(name = "Id", required = true)
    var id: String,

    @field:XmlAttribute(name = "StartDato", required = true)
    @field:XmlSchemaType(name = "date")
    @field:XmlJavaTypeAdapter(
        LocalDateAdapter::class
    )
    var startDato: LocalDate,

    @field:XmlElement(name = "Lovhjemmel", required = true)
    var lovhjemmel: LovhjemmelType,

    @field:XmlElement(name = "JmfrLovhjemmel")
    var jmfrLovhjemmel: List<LovhjemmelType>? = null,

    @field:XmlElement(name = "Krav")
    var krav: List<OversendelsePrivatKravType>? = null,

    @field:XmlElement(name = "Status")
    var status: List<VedtakStatusType>? = null,

    @field:XmlElement(name = "Konklusjon")
    var konklusjon: VedtakKonklusjonType? = null
)
