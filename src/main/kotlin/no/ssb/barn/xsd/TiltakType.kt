package no.ssb.barn.xsd

import no.ssb.barn.converter.LocalDateTimeAdapter
import no.ssb.barn.converter.UuidAdapter
import java.time.ZonedDateTime
import java.util.*
import javax.xml.bind.annotation.*
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
    name = "TiltakType",
    propOrder = ["id", "migrertId", "startDato",
        "lovhjemmel", "jmfrLovhjemmel", "kategori", "tilsyn", "oppfolging", "opphevelse", "konklusjon"]
)
open class TiltakType(
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
    ) var startDato: ZonedDateTime? = null,

    @field:XmlElement(name = "Lovhjemmel", required = true)
    var lovhjemmel: LovhjemmelType = LovhjemmelType(),

    @field:XmlElement(name = "JmfrLovhjemmel")
    var jmfrLovhjemmel: MutableList<LovhjemmelType> = mutableListOf(),

    @field:XmlElement(name = "Kategori", required = true)
    var kategori: KategoriType = KategoriType(),

    @field:XmlElement(name = "Tilsyn")
    var tilsyn: MutableList<TilsynType> = mutableListOf(),

    @field:XmlElement(name = "Oppfolging")
    var oppfolging: MutableList<OppfolgingType> = mutableListOf(),

    @field:XmlElement(name = "Opphevelse")
    var opphevelse: OpphevelseType? = null,

    @field:XmlElement(name = "Konklusjon")
    var konklusjon: TiltakKonklusjonType? = null,
)