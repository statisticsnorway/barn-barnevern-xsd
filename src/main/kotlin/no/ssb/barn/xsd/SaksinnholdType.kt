@file:Suppress("SpellCheckingInspection")

package no.ssb.barn.xsd

import no.ssb.barn.util.TypeUtils
import java.time.LocalDate
import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlAttribute
import javax.xml.bind.annotation.XmlType

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
    name = "SaksinnholdType",
    propOrder = ["kode", "presisering"]
)
data class SaksinnholdType(
    @field:XmlAttribute(name = "Kode", required = true)
    val kode: String,

    @field:XmlAttribute(name = "Presisering")
    val presisering: String?
) {
    companion object {

        @JvmStatic
        fun getCodes(date: LocalDate): List<CodeListItem> =
            TypeUtils.getCodes(date, codeList)

        @JvmStatic
        fun harPresisering(date: LocalDate): Boolean =
            getCodes(date).any { codeListItem: CodeListItem ->
                codeListItem.description.contains(
                    "krever presisering"
                )
            }

        @JvmStatic
        fun getRandomCode(date: LocalDate): String =
            getCodes(date)
                .filter { item -> !item.description.contains("krever presisering") }
                .ifEmpty { return "" }
                .random()
                .code

        private val validFrom2013 = LocalDate.parse("2013-01-01")
        private val validTo2020 = LocalDate.parse("2020-12-31")
        private val validFrom2021 = LocalDate.parse("2021-01-01")

        private val codeList =
            listOf(
                CodeListItem("1", "Foreldres somatiske sykdom", validFrom2013),
                CodeListItem(
                    "2",
                    "Foreldres psykiske problem/ lidelse",
                    validFrom2013
                ),
                CodeListItem("3", "Foreldres rusmisbruk", validFrom2013),
                CodeListItem(
                    "4",
                    "Foreldres manglende foreldreferdigheter",
                    validFrom2013,
                    validTo2020,
                    "4 deles i 20, 21, 22, 23 og 24"
                ),
                CodeListItem("5", "Foreldres kriminalitet", validFrom2013),
                CodeListItem("6", "Høy grad av konflikt hjemme", validFrom2013),
                CodeListItem(
                    "7",
                    "Vold i hjemmet/ barnet vitne til vold i nære relasjoner ",
                    validFrom2013
                ),
                CodeListItem(
                    "8",
                    "Foreldres manglende ivaretakelse av grunnleggende og hverdagslig omsorg for barnet",
                    validFrom2013
                ),
                CodeListItem(
                    "9",
                    "Barnet utsatt for fysisk vold",
                    validFrom2013
                ),
                CodeListItem(
                    "10",
                    "Barnet utsatt for psykisk vold",
                    validFrom2013
                ),
                CodeListItem(
                    "11",
                    "Barnet utsatt for seksuelle overgrep",
                    validFrom2013
                ),
                CodeListItem(
                    "12",
                    "Barnet mangler omsorgsperson",
                    validFrom2013
                ),
                CodeListItem(
                    "13",
                    "Barnet har nedsatt funksjonsevne",
                    validFrom2013,
                    validTo2020
                ),
                CodeListItem(
                    "14",
                    "Barnets psykiske problem/lidelse",
                    validFrom2013
                ),
                CodeListItem("15", "Barnets rusmisbruk", validFrom2013),
                CodeListItem(
                    "16",
                    "Barnets atferd/ kriminalitet",
                    validFrom2013,
                    validTo2020,
                    "16 deles i 25 og 26"
                ),
                CodeListItem(
                    "17",
                    "Barnets relasjonsvansker(mistanke om eller diagnostiserte tilknytningsvansker, problematikk knyttet til samspillet mellom barn og omsorgspersoner)",
                    validFrom2013
                ),
                CodeListItem(
                    "18",
                    "Andre forhold ved foreldre/ familien (krever presisering)(Denne kategorien skal kunne benyttes dersom ingen av kategoriene 1-27 passer.)",
                    validFrom2013
                ),
                CodeListItem(
                    "19",
                    "Andre forhold ved barnets situasjon (krever presisering)(Denne kategorien skal kunne benyttes dersom ingen av kategoriene 1-27 passer.)",
                    validFrom2013
                ),
                CodeListItem(
                    "20",
                    "Foreldres manglende beskyttelse av barnet",
                    validFrom2021
                ),
                CodeListItem(
                    "21",
                    "Foreldres manglende stimulering og regulering av barnet",
                    validFrom2021
                ),
                CodeListItem(
                    "22",
                    "Foreldres manglende sensitivitet og følelsesmessige tilgjengelighet for barnet",
                    validFrom2021
                ),
                CodeListItem(
                    "23",
                    "Foreldres manglende oppfølging av barnets behov for barnehage, skole og pedagogiske tjenester",
                    validFrom2021
                ),
                CodeListItem(
                    "24",
                    "Konflikt mellom foreldre som ikke bor sammen",
                    validFrom2021
                ),
                CodeListItem(
                    "28",
                    "Foreldres manglende oppfølging av barnets behov for helsetjenester",
                    validFrom2021
                ),
                CodeListItem("25", "Barnets atferd", validFrom2021),
                CodeListItem(
                    "26",
                    "Barnets kriminelle handlinger",
                    validFrom2021
                ),
                CodeListItem(
                    "27",
                    "Barnet utsatt for menneskehandel",
                    validFrom2021
                )
            )
    }
}
