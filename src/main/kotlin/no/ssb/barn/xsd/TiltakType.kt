package no.ssb.barn.xsd

import javax.xml.bind.annotation.*
import javax.xml.datatype.XMLGregorianCalendar

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TiltakType", propOrder = ["id", "migrertId", "startDato",
    "lovhjemmel", "jmfrLovhjemmel", "kategori", "tilsyn", "oppfolging", "opphevelse", "konklusjon"])
data class TiltakType(
        @XmlAttribute(name = "Id", required = true)
        var id: String? = null,

        @XmlAttribute(name = "MigrertId")
        var migrertId: String? = null,

        @XmlAttribute(name = "StartDato", required = true)
        @XmlSchemaType(name = "date")
        var startDato: XMLGregorianCalendar? = null,

        @XmlElement(name = "Lovhjemmel", required = true)
        var lovhjemmel: LovhjemmelType? = null,

        @XmlElement(name = "JmfrLovhjemmel")
        var jmfrLovhjemmel: List<LovhjemmelType>? = null,

        @XmlElement(name = "Kategori", required = true)
        var kategori: KategoriType? = null,

        @XmlElement(name = "Tilsyn")
        var tilsyn: List<TilsynType>? = null,

        @XmlElement(name = "Oppfolging")
        var oppfolging: List<OppfolgingType>? = null,

        @XmlElement(name = "Opphevelse")
        var opphevelse: OpphevelseType? = null,

        @XmlElement(name = "Konklusjon")
        var konklusjon: TiltakKonklusjonType? = null
)
