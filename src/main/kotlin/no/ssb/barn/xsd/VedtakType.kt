package no.ssb.barn.xsd

import no.ssb.barn.converter.LocalDateTimeAdapter
import no.ssb.barn.converter.UuidAdapter
import java.time.LocalDateTime
import java.util.*
import javax.xml.bind.annotation.*
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
    name = "VedtakType",
    propOrder = ["id", "startDato", "lovhjemmel", "jmfrLovhjemmel", "krav", "status", "konklusjon"]
)
data class VedtakType(
    @field:XmlAttribute(name = "Id", required = true)
    @field:XmlJavaTypeAdapter(
        UuidAdapter::class
    )
    var id: UUID = UUID.randomUUID(),

    @field:XmlAttribute(name = "StartDato", required = true)
    @field:XmlSchemaType(name = "date")
    @field:XmlJavaTypeAdapter(
        LocalDateTimeAdapter::class
    )
    var startDato: LocalDateTime = LocalDateTime.now(),

    @field:XmlElement(name = "Lovhjemmel", required = true)
    var lovhjemmel: LovhjemmelType = LovhjemmelType(),

    @field:XmlElement(name = "JmfrLovhjemmel")
    var jmfrLovhjemmel: List<LovhjemmelType> = mutableListOf(),

    @field:XmlElement(name = "Krav")
    var krav: List<OversendelsePrivatKravType> = mutableListOf(),

    @field:XmlElement(name = "Status")
    var status: List<VedtakStatusType> = mutableListOf(),

    @field:XmlElement(name = "Konklusjon")
    var konklusjon: VedtakKonklusjonType? = null
)
