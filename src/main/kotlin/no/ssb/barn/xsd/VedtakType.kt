package no.ssb.barn.xsd

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty
import jakarta.xml.bind.annotation.*
import java.time.LocalDate
import java.util.UUID

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
    name = "VedtakType",
    propOrder = ["id", "migrertId", "startDato", "erSlettet",
        "lovhjemmel", "jmfrLovhjemmel", "krav", "status", "konklusjon"]
)
data class VedtakType(
    @field:XmlAttribute(name = "Id", required = true)
    val id: UUID,

    @field:XmlAttribute(name = "MigrertId")
    val migrertId: String? = null,

    @field:XmlAttribute(name = "StartDato", required = true)
    @field:XmlSchemaType(name = "date")
    var startDato: LocalDate,

    @field:XmlAttribute(name = "ErSlettet")
    val erSlettet: Boolean = false,

    @field:XmlElement(name = "Lovhjemmel", required = true)
    val lovhjemmel: LovhjemmelType,

    @field:JacksonXmlProperty(localName = "JmfrLovhjemmel")
    @field:JacksonXmlElementWrapper(useWrapping = false)
    val jmfrLovhjemmel: MutableList<LovhjemmelType> = mutableListOf(),

    @field:JacksonXmlProperty(localName = "Krav")
    @field:JacksonXmlElementWrapper(useWrapping = false)
    val krav: MutableList<OversendelsePrivatKravType> = mutableListOf(),

    @field:JacksonXmlProperty(localName = "Status")
    @field:JacksonXmlElementWrapper(useWrapping = false)
    val status: MutableList<VedtakStatusType> = mutableListOf(),

    @field:XmlElement(name = "Konklusjon")
    var konklusjon: VedtakKonklusjonType? = null
)
