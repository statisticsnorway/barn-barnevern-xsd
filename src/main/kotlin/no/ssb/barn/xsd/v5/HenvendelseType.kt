package no.ssb.barn.xsd.v5

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper
import jakarta.xml.bind.annotation.*
import java.time.LocalDate

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
    name = "HenvendelseType",
    propOrder = ["id", "migrertId", "startDato", "erSlettet", "kode", "melder", "saksinnhold", "konklusjon"
    ]
)
data class HenvendelseType(
    @field:XmlElement(name = "Id", required = true)
    val id: String,

    @field:XmlElement(name = "MigrertId")
    val migrertId: String? = null,

    @field:XmlElement(name = "StartDato", required = true)
    @XmlSchemaType(name = "date")
    var startDato: LocalDate,

    @field:XmlElement(name = "ErSlettet")
    var erSlettet: Boolean? = null,

    @field:XmlElement(name = "Kode")
    var kode: String? = null,

    @field:XmlElement(name = "Melder")
    @field:JacksonXmlElementWrapper(useWrapping = false)
    var melder: MutableList<MelderType> = arrayListOf(),

    @field:XmlElement(name = "Saksinnhold")
    @field:JacksonXmlElementWrapper(useWrapping = false)
    var saksinnhold: MutableList<String> = arrayListOf(),

    @field:XmlElement(name = "Konklusjon")
    var konklusjon: HenvendelseKonklusjonType? = null
)