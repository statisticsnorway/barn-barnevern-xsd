package no.ssb.barn.xsd

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty
import java.time.LocalDate
import java.util.*
import javax.xml.bind.annotation.*


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
    name = "TiltakType",
    propOrder = ["id", "migrertId", "startDato",
        "lovhjemmel", "jmfrLovhjemmel", "kategori", "tilsyn", "oppfolging", "opphevelse", "konklusjon"]
)
open class TiltakType(
    @field:XmlAttribute(name = "Id", required = true)
    val id: UUID,

    @field:XmlAttribute(name = "MigrertId")
    val migrertId: String? = null,

    @field:XmlAttribute(name = "StartDato", required = true)
    @field:XmlSchemaType(name = "date")
    var startDato: LocalDate,

    @field:XmlElement(name = "Lovhjemmel", required = true)
    var lovhjemmel: LovhjemmelType,

    @field:JacksonXmlProperty(localName = "JmfrLovhjemmel")
    @field:JacksonXmlElementWrapper(useWrapping = false)
    val jmfrLovhjemmel: MutableList<LovhjemmelType> = mutableListOf(),

    @field:XmlElement(name = "Kategori", required = true)
    var kategori: KategoriType,

    @field:XmlElement(name = "Tilsyn", required = false)
    var tilsyn: TilsynType? = null,

    @field:JacksonXmlProperty(localName = "Oppfolging")
    @field:JacksonXmlElementWrapper(useWrapping = false)
    val oppfolging: MutableList<OppfolgingType> = mutableListOf(),

    @field:XmlElement(name = "Opphevelse")
    var opphevelse: OpphevelseType? = null,

    @field:XmlElement(name = "Konklusjon")
    var konklusjon: TiltakKonklusjonType? = null
)