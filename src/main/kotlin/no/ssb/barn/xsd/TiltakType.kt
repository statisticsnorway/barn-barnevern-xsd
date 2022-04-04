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
    var id: UUID,

    @field:XmlAttribute(name = "MigrertId")
    var migrertId: String?,

    @field:XmlAttribute(name = "StartDato", required = true)
    @field:XmlSchemaType(name = "date")
    var startDato: LocalDate,

    @field:XmlElement(name = "Lovhjemmel", required = true)
    var lovhjemmel: LovhjemmelType,

    @field:JacksonXmlProperty(localName = "JmfrLovhjemmel")
    @field:JacksonXmlElementWrapper(useWrapping = false)
    var jmfrLovhjemmel: MutableList<LovhjemmelType> = mutableListOf(),

    @field:XmlElement(name = "Kategori", required = true)
    var kategori: KategoriType,

    @field:JacksonXmlProperty(localName = "Tilsyn")
    @field:JacksonXmlElementWrapper(useWrapping = false)
    var tilsyn: MutableList<TilsynType> = mutableListOf(),

    @field:JacksonXmlProperty(localName = "Oppfolging")
    @field:JacksonXmlElementWrapper(useWrapping = false)
    var oppfolging: MutableList<OppfolgingType> = mutableListOf(),

    @field:XmlElement(name = "Opphevelse")
    var opphevelse: OpphevelseType?,

    @field:XmlElement(name = "Konklusjon")
    var konklusjon: TiltakKonklusjonType?
)