package no.ssb.barn.xsd

import no.ssb.barn.converter.LocalDateAdapter
import java.time.LocalDate
import jakarta.xml.bind.annotation.*
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter
import no.ssb.barn.generator.RandomGenerator

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
    name = "VedtakType",
    propOrder = ["id", "startDato", "lovhjemmel", "jmfrLovhjemmel", "krav", "status", "konklusjon"]
)
data class VedtakType(
    @field:XmlAttribute(name = "Id", required = true)
    var id: String = RandomGenerator.generateRandomString(10),

    @field:XmlAttribute(name = "StartDato", required = true)
    @field:XmlSchemaType(name = "date")
    @field:XmlJavaTypeAdapter(
        LocalDateAdapter::class
    )
    var startDato: LocalDate = LocalDate.now(),

    @field:XmlElement(name = "Lovhjemmel", required = true)
    var lovhjemmel: LovhjemmelType = LovhjemmelType(),

    @field:XmlElement(name = "JmfrLovhjemmel")
    var jmfrLovhjemmel: MutableList<LovhjemmelType>? = MutableList(1) { LovhjemmelType() },

    @field:XmlElement(name = "Krav")
    var krav: MutableList<OversendelsePrivatKravType>? = MutableList(1) { OversendelsePrivatKravType() },

    @field:XmlElement(name = "Status")
    var status: MutableList<VedtakStatusType>? = MutableList(1) { VedtakStatusType() },

    @field:XmlElement(name = "Konklusjon")
    var konklusjon: VedtakKonklusjonType? = VedtakKonklusjonType()
)
