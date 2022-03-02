package no.ssb.barn.xsd

import no.ssb.barn.converter.LocalDateTimeAdapter
import no.ssb.barn.converter.UuidAdapter
import java.time.ZonedDateTime
import java.util.*
import javax.xml.bind.annotation.*
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter

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
        LocalDateTimeAdapter::class
    )
    var startDato: ZonedDateTime = ZonedDateTime.now(),

    @field:XmlElement(name = "Vedtaksgrunnlag")
    var vedtaksgrunnlag: MutableList<SaksinnholdType> = mutableListOf(),

    @field:XmlElement(name = "UtvidetFrist")
    var utvidetFrist: UndersokelseUtvidetFristType? = null,

    @field:XmlElement(name = "Konklusjon")
    var konklusjon: UndersokelseKonklusjonType? = null
)
