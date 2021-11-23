package no.ssb.barn.xsd

import no.ssb.barn.codelists.CodelistItem
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneId
import java.time.ZonedDateTime
import jakarta.xml.bind.annotation.XmlAccessType
import jakarta.xml.bind.annotation.XmlAccessorType
import jakarta.xml.bind.annotation.XmlAttribute
import jakarta.xml.bind.annotation.XmlType

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SaksinnholdType",
    propOrder = ["kode", "presisering"],
    factoryMethod = "createSaksinnholdType")
data class SaksinnholdType(
    @field:XmlAttribute(name = "Kode", required = true)
    var kode: String,

    @field:XmlAttribute(name = "Presisering")
    var presisering: String? = null
) {
    companion object {
        @JvmStatic
        fun createSaksinnholdType(): SaksinnholdType{
            return SaksinnholdType(
                "1",
                null
            )
        }

        @JvmStatic
        fun getCodes(date: LocalDate): List<CodelistItem> {
            return listOf(
                CodelistItem(
                    "1",
                    "Foreldres somatiske sykdom",
                    LocalDate.of(2013, 1, 1)
                ),
                CodelistItem(
                    "2",
                    "Foreldres psykiske problem/ lidelse",
                    LocalDate.of(2013, 1, 1)
                ),
                CodelistItem(
                    "3",
                    "Foreldres rusmisbruk",
                    LocalDate.of(2013, 1, 1)
                ),
                CodelistItem(
                    "4",
                    "Foreldres manglende foreldreferdigheter",
                    LocalDate.of(2013, 1, 1),
                    LocalDate.of(2019, 12, 31),
                    "4 deles i 20, 21, 22, 23 og 24"
                ),
                CodelistItem(
                    "20",
                    "Foreldres manglende beskyttelse av barnet",
                    LocalDate.of(2020, 1, 1)
                ),
                CodelistItem(
                    "21",
                    "Foreldres manglende stimulering og regulering av barnet",
                    LocalDate.of(2020, 1, 1)
                ),
                CodelistItem(
                    "22",
                    "Foreldres manglende sensitivitet og følelsesmessige tilgjengelighet for barnet",
                    LocalDate.of(2020, 1, 1)
                ),
                CodelistItem(
                    "23",
                    "Foreldres manglende oppfølging av barnets behov for barnehage, skole og pedagogiske tjenester",
                    LocalDate.of(2020, 1, 1)
                ),
                CodelistItem(
                    "24",
                    "Konflikt mellom foreldre som ikke bor sammen",
                    LocalDate.of(2020, 1, 1)
                ),
                CodelistItem(
                    "5",
                    "Foreldres kriminalitet",
                    LocalDate.of(2013, 1, 1)
                ),
                CodelistItem(
                    "6",
                    "Høy grad av konflikt hjemme",
                    LocalDate.of(2013, 1, 1)
                ),
                CodelistItem(
                    "7",
                    "Vold i hjemmet/ barnet vitne til vold i nære relasjoner ",
                    LocalDate.of(2013, 1, 1)
                ),
                CodelistItem(
                    "8",
                    "Barnet utsatt for vanskjøtsel (Barnet overlatt til seg selv, dårlig kosthold, dårlig hygiene)",
                    LocalDate.of(2013, 1, 1)
                ),
                CodelistItem(
                    "9",
                    "Barnet utsatt for fysisk vold",
                    LocalDate.of(2013, 1, 1)
                ),
                CodelistItem(
                    "10",
                    "Barnet utsatt for psykisk vold",
                    LocalDate.of(2013, 1, 1)
                ),
                CodelistItem(
                    "11",
                    "Barnet utsatt for seksuelle overgrep",
                    LocalDate.of(2013, 1, 1)
                ),
                CodelistItem(
                    "12",
                    "Barnet mangler omsorgsperson",
                    LocalDate.of(2013, 1, 1)
                ),
                CodelistItem(
                    "13",
                    "Barnet har nedsatt funksjonsevne",
                    LocalDate.of(2013, 1, 1)
                ),
                CodelistItem(
                    "14",
                    "Barnets psykiske problem/lidelse",
                    LocalDate.of(2013, 1, 1)
                ),
                CodelistItem(
                    "15",
                    "Barnets rusmisbruk",
                    LocalDate.of(2013, 1, 1)
                ),
                CodelistItem(
                    "16",
                    "Barnets atferd/ kriminalitet",
                    LocalDate.of(2013, 1, 1),
                    LocalDate.of(2019, 12, 31),
                    "16 deles i 25 og 26"
                ),
                CodelistItem(
                    "17",
                    "Barnets relasjonsvansker(mistanke om eller diagnostiserte tilknytningsvansker, problematikk knyttet til samspillet mellom barn og omsorgspersoner)",
                    LocalDate.of(2013, 1, 1)
                ),
                CodelistItem(
                    "25",
                    "Barnets atferd",
                    LocalDate.of(2020, 1, 1)
                ),
                CodelistItem(
                    "26",
                    "Barnets kriminelle handlinger",
                    LocalDate.of(2020, 1, 1)
                ),
                CodelistItem(
                    "27",
                    "Barnet utsatt for menneskehandel",
                    LocalDate.of(2020, 1, 1)
                ),
                CodelistItem(
                    "18",
                    "Andre forhold ved foreldre/ familien (krever presisering)(Denne kategorien skal kunne benyttes dersom ingen av kategoriene 1-27 passer.)",
                    LocalDate.of(2013, 1, 1)
                ),
                CodelistItem(
                    "19",
                    "Andre forhold ved barnets situasjon (krever presisering)(Denne kategorien skal kunne benyttes dersom ingen av kategoriene 1-27 passer.)",
                    LocalDate.of(2013, 1, 1)
                )
            ).filter {
                (date.isEqual(it.validFrom) || date.isAfter(it.validFrom))
                        && (date.isBefore(it.validTo) || date.isEqual(it.validTo))
            }
        }

        @JvmStatic
        fun harPresisering(date: LocalDate): Boolean {
            return getCodes(date).filter { codelistItem: CodelistItem -> codelistItem.description.contains("krever presisering") }
                .isNotEmpty()
        }

        @JvmStatic
        fun getRandomCode(date: LocalDate): String {
            return MelderType.getCodes(date).filter { item -> !item.description.contains("krever presisering") }.random().code
        }
    }
}
