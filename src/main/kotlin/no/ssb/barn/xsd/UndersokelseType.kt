package no.ssb.barn.xsd

import jakarta.xml.bind.annotation.*
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter
import no.ssb.barn.converter.LocalDateAdapter
import no.ssb.barn.converter.UuidAdapter
import no.ssb.barn.generator.RandomGenerator
import java.time.LocalDate
import java.util.*

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
    name = "UndersokelseType",
    propOrder = ["id", "migrertId", "startDato", "vedtaksgrunnlag", "utvidetFrist", "konklusjon"]
)
data class UndersokelseType(
    @field:XmlAttribute(name = "Id", required = true)
    @field:XmlJavaTypeAdapter(
        UuidAdapter::class
    )
    var id: UUID = UUID.randomUUID(),

    @field:XmlAttribute(name = "MigrertId")
    var migrertId: String? = null,

    @field:XmlAttribute(name = "StartDato", required = true)
    @field:XmlSchemaType(name = "date")
    @field:XmlJavaTypeAdapter(
        LocalDateAdapter::class
    )
    var startDato: LocalDate = LocalDate.now(),

    @field:XmlElement(name = "Vedtaksgrunnlag")
    var vedtaksgrunnlag: MutableList<SaksinnholdType>? = null,

    @field:XmlElement(name = "UtvidetFrist")
    var utvidetFrist: UndersokelseUtvidetFristType? = null,

    @field:XmlElement(name = "Konklusjon")
    var konklusjon: UndersokelseKonklusjonType? = null
)
