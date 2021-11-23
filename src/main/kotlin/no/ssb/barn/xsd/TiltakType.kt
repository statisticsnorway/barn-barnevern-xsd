package no.ssb.barn.xsd

import no.ssb.barn.converter.LocalDateAdapter
import java.time.LocalDate
import javax.xml.bind.annotation.*
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
    name = "TiltakType", propOrder = ["id", "migrertId", "startDato",
        "lovhjemmel", "jmfrLovhjemmel", "kategori", "tilsyn", "oppfolging", "opphevelse", "konklusjon"]
)
data class TiltakType(
    @field:XmlAttribute(name = "Id", required = true)
    var id: String? = null,

    @field:XmlAttribute(name = "MigrertId")
    var migrertId: String? = null,

    @field:XmlAttribute(name = "StartDato", required = true)
    @field:XmlSchemaType(name = "date")
    @field:XmlJavaTypeAdapter(
        LocalDateAdapter::class
    ) var startDato: LocalDate? = null,

    @field:XmlElement(name = "Lovhjemmel", required = true)
    var lovhjemmel: LovhjemmelType? = null,

    @field:XmlElement(name = "JmfrLovhjemmel")
    var jmfrLovhjemmel: List<LovhjemmelType>? = null,

    @field:XmlElement(name = "Kategori", required = true)
    var kategori: KategoriType? = null,

    @field:XmlElement(name = "Tilsyn")
    var tilsyn: List<TilsynType>? = null,

    @field:XmlElement(name = "Oppfolging")
    var oppfolging: List<OppfolgingType>? = null,

    @field:XmlElement(name = "Opphevelse")
    var opphevelse: OpphevelseType? = null,

    @field:XmlElement(name = "Konklusjon")
    var konklusjon: TiltakKonklusjonType? = null
)
