package no.ssb.barn.xsd

import javax.xml.bind.annotation.*
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter
import no.ssb.barn.converter.LocalDateAdapter
import no.ssb.barn.converter.UuidAdapter
import no.ssb.barn.util.TypeUtils
import java.time.LocalDate
import java.util.*

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FlyttingType", propOrder = ["id", "migrertId", "sluttDato", "arsakFra", "flytteTil"])
data class FlyttingType(
    @field:XmlAttribute(name = "Id", required = true)
    @field:XmlJavaTypeAdapter(
        UuidAdapter::class
    )
    var id: UUID = UUID.randomUUID(),

    @field:XmlAttribute(name = "MigrertId")
    var migrertId: String? = null,

    @field:XmlAttribute(name = "SluttDato")
    @field:XmlSchemaType(name = "date")
    @field:XmlJavaTypeAdapter(
        LocalDateAdapter::class
    )
    var sluttDato: LocalDate? = LocalDate.now(),

    @field:XmlElement(name = "ArsakFra")
    var arsakFra: ArsakFraType = ArsakFraType(),

    @field:XmlElement(name = "FlyttingTil")
    var flytteTil: FlyttingTilType = FlyttingTilType()
) {
    companion object {
        private val validFrom: LocalDate = LocalDate.parse("2013-01-01")
        private val codeList =
            mapOf(
                Pair(
                    "1.1",
                    "Barnet tilbakeføres til foreldre/ omsorgsplasseringen opphører"
                ),
                Pair(
                    "1.2",
                    "Barnet blir myndig"
                ),
                Pair(
                    "1.3",
                    "Fosterforeldre klarer ikke å dekke barnets behov <br>" +
                            "(forhold ved fosterhjemmet)"
                ),
                Pair(
                    "1.4",
                    "Barnet har behov for annet plasseringssted <br>" +
                            "(institusjon, TFCO-fosterhjem, forsterket fosterhjem, osv. =forhold ved barnet)"
                ),
                Pair(
                    "1.5",
                    "Andre grunner; spesifiser. <br>" +
                            "(f.eks. uenighet om oppdragets omfang, økonomi, forsterkningstiltak mv.)."
                ),
                Pair(
                    "2.1",
                    "Barnet har behov fosterforeldre ikke kan dekke"
                ),
                Pair(
                    "2.2",
                    "Endring i fosterforeldres livssituasjon (skilsmisse, død, osv.)"
                ),
                Pair(
                    "2.3",
                    "Andre grunner; spesifiser <br>" +
                            "(f.eks. uenighet om oppdragets omfang, økonomi, forsterkningstiltak, <br>" +
                            "manglende eller lite effektiv veiledning, mv.)."
                )
            )
                .map {
                    CodeListItem(it.key, it.value, validFrom)
                }

        @JvmStatic
        fun getCodes(date: LocalDate): List<CodeListItem> =
            TypeUtils.getCodes(date, codeList)
    }
}
