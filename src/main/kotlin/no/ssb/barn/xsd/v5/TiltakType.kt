package no.ssb.barn.xsd.v5

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper
import jakarta.xml.bind.annotation.*
import java.time.LocalDate
import java.util.UUID

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
    name = "TiltakType",
    propOrder = ["id", "migrertId", "startDato", "erSlettet", "lovhjemmel", "jmfrLovhjemmel", "kode", "tiltaksgrunnlag", "barnetsMedvirkning", "evaluering", "samvaerEtterOmsorgsovertakelse", "tilsyn", "oppfolging", "konklusjon"
    ]
)
data class TiltakType(
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

    @field:XmlElement(name = "Kode", required = true)
    val kode: String,

    @field:XmlElement(name = "Tiltaksgrunnlag")
    @field:JacksonXmlElementWrapper(useWrapping = false)
    val tiltaksgrunnlag: MutableList<String> = mutableListOf(),

    @field:XmlElement(name = "BarnetsMedvirkning")
    @field:JacksonXmlElementWrapper(useWrapping = false)
    val barnetsMedvirkning: MutableList<BarnetsMedvirkningType> = mutableListOf(),

    @field:XmlElement(name = "Evaluering")
    @field:JacksonXmlElementWrapper(useWrapping = false)
    val evaluering: MutableList<TiltakEvalueringType> = mutableListOf(),

    @field:XmlElement(name = "SamvaerEtterOmsorgsovertakelse")
    val samvaerEtterOmsorgsovertakelse: SamvaerEtterOmsorgsovertakelseType? = null,

    @field:XmlElement(name = "Tilsyn")
    val tilsyn: TilsynType? = null,

    @field:XmlElement(name = "Oppfolging")
    val oppfolging: OppfolgingType? = null,

    @field:XmlElement(name = "Konklusjon")
    val konklusjon: TiltakKonklusjonType? = null
)