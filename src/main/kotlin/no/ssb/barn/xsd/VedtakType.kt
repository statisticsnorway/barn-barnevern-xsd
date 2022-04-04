package no.ssb.barn.xsd

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty
import java.time.LocalDate
import java.util.*
import javax.xml.bind.annotation.*

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
    name = "VedtakType",
    propOrder = ["id", "startDato", "lovhjemmel", "jmfrLovhjemmel", "krav", "status", "konklusjon"]
)
data class VedtakType(
    @field:XmlAttribute(name = "Id", required = true)
    var id: UUID,

    @field:XmlAttribute(name = "StartDato", required = true)
    @field:XmlSchemaType(name = "date")
    var startDato: LocalDate,

    @field:XmlElement(name = "Lovhjemmel", required = true)
    var lovhjemmel: LovhjemmelType,

    @field:JacksonXmlProperty(localName = "JmfrLovhjemmel")
    @field:JacksonXmlElementWrapper(useWrapping = false)
    var jmfrLovhjemmel: List<LovhjemmelType> = listOf(),

    @field:JacksonXmlProperty(localName = "Krav")
    @field:JacksonXmlElementWrapper(useWrapping = false)
    var krav: List<OversendelsePrivatKravType> = listOf(),

    @field:JacksonXmlProperty(localName = "Status")
    @field:JacksonXmlElementWrapper(useWrapping = false)
    var status: List<VedtakStatusType> = listOf(),

    @field:XmlElement(name = "Konklusjon")
    var konklusjon: VedtakKonklusjonType?
)
