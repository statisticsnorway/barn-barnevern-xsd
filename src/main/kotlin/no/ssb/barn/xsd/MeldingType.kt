package no.ssb.barn.xsd

import javax.xml.bind.annotation.*
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter
import no.ssb.barn.converter.LocalDateAdapter
import no.ssb.barn.converter.UuidAdapter
import java.time.LocalDate
import java.util.*

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
    var id: UUID = UUID.randomUUID(),

    @field:XmlAttribute(name = "MigrertId")
    var migrertId: String? = null,

    @field:XmlAttribute(name = "StartDato", required = true)
    @field:XmlSchemaType(name = "date")
    @field:XmlJavaTypeAdapter(
        LocalDateAdapter::class
    )
    var startDato: LocalDate = LocalDate.now(),

    @field:XmlElement(name = "Melder")
    var melder: MutableList<MelderType>? = MutableList(1) { MelderType() },

    @field:XmlElement(name = "Saksinnhold")
    var saksinnhold: MutableList<SaksinnholdType>? = MutableList(1) { SaksinnholdType() },

    @field:XmlElement(name = "Konklusjon")
    var konklusjon: MeldingKonklusjonType? = null
)