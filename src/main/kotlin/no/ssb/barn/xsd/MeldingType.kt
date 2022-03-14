package no.ssb.barn.xsd

import no.ssb.barn.converter.LocalDateTimeAdapter
import no.ssb.barn.converter.UuidAdapter
import java.time.ZonedDateTime
import java.util.*
import javax.xml.bind.annotation.*
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
    name = "MeldingType",
    propOrder = ["id", "migrertId", "startDato", "melder", "saksinnhold", "konklusjon"]
)
data class MeldingType(
    @field:XmlAttribute(name = "Id", required = true)
    @field:XmlJavaTypeAdapter(
        UuidAdapter::class
    )
    var id: UUID? = null,

    @field:XmlAttribute(name = "MigrertId")
    var migrertId: String? = null,

    @field:XmlAttribute(name = "StartDato", required = true)
    @field:XmlSchemaType(name = "dateTime")
    @field:XmlJavaTypeAdapter(
        LocalDateTimeAdapter::class
    )
    var startDato: ZonedDateTime = ZonedDateTime.now(),

    @field:XmlElement(name = "Melder")
    var melder: MutableList<MelderType> = mutableListOf(),

    @field:XmlElement(name = "Saksinnhold")
    var saksinnhold: MutableList<SaksinnholdType> = mutableListOf(),

    @field:XmlElement(name = "Konklusjon")
    var konklusjon: MeldingKonklusjonType? = null
)
