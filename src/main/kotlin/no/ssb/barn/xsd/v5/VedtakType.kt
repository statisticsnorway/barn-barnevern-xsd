package no.ssb.barn.xsd.v5

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper
import jakarta.xml.bind.annotation.*
import java.time.LocalDate
import java.util.UUID

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
    name = "VedtakType",
    propOrder = ["id", "migrertId", "startDato", "erSlettet", "lovhjemmel", "jmfrLovhjemmel", "krav", "status", "bortfallsdatoVedtak", "nyBortfallsdatoVedtak"
    ]
)
data class VedtakType(
    @field:XmlElement(name = "Id", required = true)
    val id: UUID,

    @field:XmlElement(name = "MigrertId")
    val migrertId: String? = null,

    @field:XmlElement(name = "StartDato", required = true)
    @XmlSchemaType(name = "date")
    val startDato: LocalDate,

    @field:XmlElement(name = "ErSlettet")
    val erSlettet: Boolean = false,

    @field:XmlElement(name = "Lovhjemmel", required = true)
    val lovhjemmel: LovhjemmelType,

    @field:XmlElement(name = "JmfrLovhjemmel")
    @field:JacksonXmlElementWrapper(useWrapping = false)
    val jmfrLovhjemmel: MutableList<LovhjemmelType> = mutableListOf(),

    @field:XmlElement(name = "Krav")
    @field:JacksonXmlElementWrapper(useWrapping = false)
    val krav: MutableList<OversendelsePrivatKravType> = mutableListOf(),

    @field:XmlElement(name = "Status", required = true)
    @field:JacksonXmlElementWrapper(useWrapping = false)
    val status: MutableList<VedtakStatusType>,

    @field:XmlElement(name = "BortfallsdatoVedtak")
    @XmlSchemaType(name = "date")
    val bortfallsdatoVedtak: LocalDate? = null,

    @field:XmlElement(name = "NyBortfallsdatoVedtak")
    @XmlSchemaType(name = "date")
    val nyBortfallsdatoVedtak: LocalDate? = null
)