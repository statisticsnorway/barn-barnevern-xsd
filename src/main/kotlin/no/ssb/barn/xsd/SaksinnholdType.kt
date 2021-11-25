package no.ssb.barn.xsd

import jakarta.xml.bind.annotation.*
import no.ssb.barn.codelists.CodeListItem
import java.time.LocalDate

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
    name = "SaksinnholdType",
    propOrder = ["kode", "presisering"]
)
data class SaksinnholdType(
    @field:XmlAttribute(name = "Kode", required = true)
    var kode: String = getCodes(LocalDate.now())[0].code,

    @field:XmlAttribute(name = "Presisering")
    var presisering: String? = null
) {
    companion object {
        @JvmStatic
        fun getCodes(date: LocalDate): List<CodeListItem> {
            return listOf(
                CodeListItem(
                    "1",
                    "Foreldres somatiske sykdom",
                    LocalDate.of(2013, 1, 1)
                ),
                CodeListItem(
                    "2",
                    "Foreldres psykiske problem/ lidelse",
                    LocalDate.of(2013, 1, 1)
                ),
                CodeListItem(
                    "3",
                    "Foreldres rusmisbruk",
                    LocalDate.of(2013, 1, 1)
                ),
                CodeListItem(
                    "4",
                    "Foreldres manglende foreldreferdigheter",
                    LocalDate.of(2013, 1, 1),
                    LocalDate.of(2019, 12, 31),
                    "4 deles i 20, 21, 22, 23 og 24"
                ),
                CodeListItem(
                    "20",
                    "Foreldres manglende beskyttelse av barnet",
                    LocalDate.of(2020, 1, 1)
                ),
                CodeListItem(
                    "21",
                    "Foreldres manglende stimulering og regulering av barnet",
                    LocalDate.of(2020, 1, 1)
                ),
                CodeListItem(
                    "22",
                    "Foreldres manglende sensitivitet og følelsesmessige tilgjengelighet for barnet",
                    LocalDate.of(2020, 1, 1)
                ),
                CodeListItem(
                    "23",
                    "Foreldres manglende oppfølging av barnets behov for barnehage, skole og pedagogiske tjenester",
                    LocalDate.of(2020, 1, 1)
                ),
                CodeListItem(
                    "24",
                    "Konflikt mellom foreldre som ikke bor sammen",
                    LocalDate.of(2020, 1, 1)
                ),
                CodeListItem(
                    "5",
                    "Foreldres kriminalitet",
                    LocalDate.of(2013, 1, 1)
                ),
                CodeListItem(
                    "6",
                    "Høy grad av konflikt hjemme",
                    LocalDate.of(2013, 1, 1)
                ),
                CodeListItem(
                    "7",
                    "Vold i hjemmet/ barnet vitne til vold i nære relasjoner ",
                    LocalDate.of(2013, 1, 1)
                ),
                CodeListItem(
                    "8",
                    "Barnet utsatt for vanskjøtsel (Barnet overlatt til seg selv, dårlig kosthold, dårlig hygiene)",
                    LocalDate.of(2013, 1, 1)
                ),
                CodeListItem(
                    "9",
                    "Barnet utsatt for fysisk vold",
                    LocalDate.of(2013, 1, 1)
                ),
                CodeListItem(
                    "10",
                    "Barnet utsatt for psykisk vold",
                    LocalDate.of(2013, 1, 1)
                ),
                CodeListItem(
                    "11",
                    "Barnet utsatt for seksuelle overgrep",
                    LocalDate.of(2013, 1, 1)
                ),
                CodeListItem(
                    "12",
                    "Barnet mangler omsorgsperson",
                    LocalDate.of(2013, 1, 1)
                ),
                CodeListItem(
                    "13",
                    "Barnet har nedsatt funksjonsevne",
                    LocalDate.of(2013, 1, 1)
                ),
                CodeListItem(
                    "14",
                    "Barnets psykiske problem/lidelse",
                    LocalDate.of(2013, 1, 1)
                ),
                CodeListItem(
                    "15",
                    "Barnets rusmisbruk",
                    LocalDate.of(2013, 1, 1)
                ),
                CodeListItem(
                    "16",
                    "Barnets atferd/ kriminalitet",
                    LocalDate.of(2013, 1, 1),
                    LocalDate.of(2019, 12, 31),
                    "16 deles i 25 og 26"
                ),
                CodeListItem(
                    "17",
                    "Barnets relasjonsvansker(mistanke om eller diagnostiserte tilknytningsvansker, problematikk knyttet til samspillet mellom barn og omsorgspersoner)",
                    LocalDate.of(2013, 1, 1)
                ),
                CodeListItem(
                    "25",
                    "Barnets atferd",
                    LocalDate.of(2020, 1, 1)
                ),
                CodeListItem(
                    "26",
                    "Barnets kriminelle handlinger",
                    LocalDate.of(2020, 1, 1)
                ),
                CodeListItem(
                    "27",
                    "Barnet utsatt for menneskehandel",
                    LocalDate.of(2020, 1, 1)
                ),
                CodeListItem(
                    "18",
                    "Andre forhold ved foreldre/ familien (krever presisering)(Denne kategorien skal kunne benyttes dersom ingen av kategoriene 1-27 passer.)",
                    LocalDate.of(2013, 1, 1)
                ),
                CodeListItem(
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
            return getCodes(date).filter { codelistItem: CodeListItem -> codelistItem.description.contains("krever presisering") }
                .isNotEmpty()
        }

        @JvmStatic
        fun getRandomCode(date: LocalDate): String {
            return MelderType.getCodes(date).filter { item -> !item.description.contains("krever presisering") }
                .random().code
        }
    }
}
