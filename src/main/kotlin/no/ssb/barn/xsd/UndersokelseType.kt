package no.ssb.barn.xsd

import jakarta.xml.bind.annotation.*
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter
import no.ssb.barn.converter.LocalDateAdapter
import java.time.LocalDate

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
    name = "UndersokelseType", propOrder = ["id", "migrertId", "startDato",
        "vedtaksgrunnlag", "utvidetFrist", "konklusjon"]
)
data class UndersokelseType(
    @field:XmlAttribute(name = "Id", required = true)
    var id: String,

    @field:XmlAttribute(name = "MigrertId")
    var migrertId: String? = null,

    @field:XmlAttribute(name = "StartDato", required = true)
    @field:XmlSchemaType(name = "date")
    @field:XmlJavaTypeAdapter(
        LocalDateAdapter::class
    )
    var startDato: LocalDate,

    @field:XmlElement(name = "Vedtaksgrunnlag")
    var vedtaksgrunnlag: MutableList<SaksinnholdType>? = null,

    @field:XmlElement(name = "UtvidetFrist")
    var utvidetFrist: UndersokelseUtvidetFristType? = null,

    @field:XmlElement(name = "Konklusjon")
    var konklusjon: UndersokelseKonklusjonType? = null
)
