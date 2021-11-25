package no.ssb.barn.xsd

import no.ssb.barn.codelists.CodeListItem
import no.ssb.barn.converter.LocalDateAdapter
import java.time.LocalDate
import jakarta.xml.bind.annotation.*
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter
import no.ssb.barn.generator.RandomGenerator

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FlyttingType", propOrder = ["id", "sluttDato", "kode"])
data class FlyttingType(
    @field:XmlAttribute(name = "Id", required = true)
    var id: String = RandomGenerator.generateRandomString(10),

    @field:XmlAttribute(name = "SluttDato")
    @field:XmlSchemaType(name = "date")
    @field:XmlJavaTypeAdapter(
        LocalDateAdapter::class
    )
    var sluttDato: LocalDate? = LocalDate.now(),

    @field:XmlAttribute(name = "Kode", required = true)
    var kode: String? = getCodes(LocalDate.now())[0].code
) {
    companion object {
        @JvmStatic
        fun getCodes(date: LocalDate): List<CodeListItem> {
            return listOf(
                CodeListItem(
                    "1.1",
                    "Barnet tilbakeføres til foreldre/ omsorgsplasseringen opphører",
                    LocalDate.parse("2013-01-01")
                ),
                CodeListItem(
                    "1.2",
                    "Barnet blir myndig",
                    LocalDate.parse("2013-01-01")
                ),
                CodeListItem(
                    "1.3",
                    "Fosterforeldre klarer ikke å dekke barnets behov <br>" +
                            "(forhold ved fosterhjemmet)",
                    LocalDate.parse("2013-01-01")
                ),
                CodeListItem(
                    "1.4",
                    "Barnet har behov for annet plasseringssted <br>" +
                            "(institusjon, TFCO-fosterhjem, forsterket fosterhjem, osv. =forhold ved barnet)",
                    LocalDate.parse("2013-01-01")
                ),
                CodeListItem(
                    "1.5",
                    "Andre grunner; spesifiser. <br>" +
                            "(f.eks. uenighet om oppdragets omfang, økonomi, forsterkningstiltak mv.).",
                    LocalDate.parse("2013-01-01")
                ),
                CodeListItem(
                    "2.1",
                    "Barnet har behov fosterforeldre ikke kan dekke",
                    LocalDate.parse("2013-01-01")
                ),
                CodeListItem(
                    "2.2",
                    "Endring i fosterforeldres livssituasjon (skilsmisse, død, osv.)",
                    LocalDate.parse("2013-01-01")
                ),
                CodeListItem(
                    "2.3",
                    "Andre grunner; spesifiser <br>" +
                            "(f.eks. uenighet om oppdragets omfang, økonomi, forsterkningstiltak, <br>" +
                            "manglende eller lite effektiv veiledning, mv.).",
                    LocalDate.parse("2013-01-01")
                )
            ).filter {
                (date.isEqual(it.validFrom) || date.isAfter(it.validFrom))
                        && (date.isBefore(it.validTo) || date.isEqual(it.validTo))
            }
        }
    }
}
