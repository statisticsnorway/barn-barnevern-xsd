package no.ssb.barn.xsd

import no.ssb.barn.converter.LocalDateTimeAdapter
import no.ssb.barn.converter.UuidAdapter
import no.ssb.barn.util.TypeUtils
import java.time.LocalDate
import java.time.ZonedDateTime
import java.util.*
import javax.xml.bind.annotation.*
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
    name = "FlyttingType",
    propOrder = ["id", "migrertId", "sluttDato", "arsakFra", "flytteTil"]
)
data class FlyttingType(
    @field:XmlAttribute(name = "Id", required = true)
    @field:XmlJavaTypeAdapter(
        UuidAdapter::class
    )
    var id: UUID? = null,

    @field:XmlAttribute(name = "MigrertId")
    var migrertId: String? = null,

    @field:XmlAttribute(name = "SluttDato")
    @field:XmlSchemaType(name = "dateTime")
    @field:XmlJavaTypeAdapter(LocalDateTimeAdapter::class)
    var sluttDato: ZonedDateTime? = null,

    @field:XmlElement(name = "ArsakFra")
    var arsakFra: ArsakFraType = ArsakFraType(),

    @field:XmlElement(name = "FlyttingTil")
    var flytteTil: FlyttingTilType = FlyttingTilType()
) {
    companion object {

        @JvmStatic
        fun getCodes(date: LocalDate): List<CodeListItem> =
            TypeUtils.getCodes(date, codeList)

        private val validFrom: LocalDate = LocalDate.parse("2013-01-01")

        private val codeList =
            mapOf(
                "1.1" to "Barnet tilbakeføres til foreldre/ omsorgsplasseringen opphører",
                "1.2" to "Barnet blir myndig",
                "1.3" to "Fosterforeldre klarer ikke å dekke barnets behov <br>" +
                        "(forhold ved fosterhjemmet)",
                "1.4" to "Barnet har behov for annet plasseringssted <br>" +
                        "(institusjon, TFCO-fosterhjem, forsterket fosterhjem, osv. =forhold ved barnet)",
                "1.5" to "Andre grunner; spesifiser. <br>" +
                        "(f.eks. uenighet om oppdragets omfang, økonomi, forsterkningstiltak mv.).",
                "2.1" to "Barnet har behov fosterforeldre ikke kan dekke",
                "2.2" to "Endring i fosterforeldres livssituasjon (skilsmisse, død, osv.)",
                "2.3" to "Andre grunner; spesifiser <br>" +
                        "(f.eks. uenighet om oppdragets omfang, økonomi, forsterkningstiltak, <br>" +
                        "manglende eller lite effektiv veiledning, mv.)."
            )
                .map {
                    CodeListItem(it.key, it.value, validFrom)
                }
    }
}
