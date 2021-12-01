package no.ssb.barn.xsd

import jakarta.xml.bind.annotation.XmlAccessType
import jakarta.xml.bind.annotation.XmlAccessorType
import jakarta.xml.bind.annotation.XmlAttribute
import jakarta.xml.bind.annotation.XmlType
import no.ssb.barn.codelists.CodeListItem
import no.ssb.barn.util.TypeUtils
import java.time.LocalDate

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
    name = "MelderType",
    propOrder = ["kode", "presisering"]
)
data class MelderType(
    @field:XmlAttribute(name = "Kode", required = true)
    var kode: String = getCodes(LocalDate.now())[0].code,

    @field:XmlAttribute(name = "Presisering")
    var presisering: String? = ""
) {
    companion object {
        private val validFrom: LocalDate = LocalDate.parse("2013-01-01")
        private val codeList = mapOf(
            Pair(
                "1",
                "Barnet selv"
            ),
            Pair(
                "2",
                "Mor/ far/ foresatte"
            ),
            Pair(
                "3",
                "Familie for øvrig"
            ),
            Pair(
                "4",
                "Andre privatpersoner"
            ),
            Pair(
                "5",
                "Barnvernstjenesten"
            ),
            Pair(
                "6",
                "NAV (kommune og stat)"
            ),
            Pair(
                "7",
                "Barnevernsvakt"
            ),
            Pair(
                "8",
                "Politi"
            ),
            Pair(
                "9",
                "Barnehage"
            ),
            Pair(
                "10",
                "Helsestasjon/skolehelsetjenesten"
            ),
            Pair(
                "11",
                "Skole"
            ),
            Pair(
                "12",
                "Pedagogisk-psykologisk tjeneste (PPT)"
            ),
            Pair(
                "13",
                "Psykisk helsevern for barn og unge (kommune og stat)"
            ),
            Pair(
                "14",
                "Psykisk helsevern for voksne (kommune og stat)"
            ),
            Pair(
                "15",
                "Lege/ sykehus/ tannlege"
            ),
            Pair(
                "16",
                "Familievernkontor"
            ),
            Pair(
                "17",
                "Tjenester og instanser med ansvar for oppfølging av personers rusproblemer (kommune og stat)"
            ),
            Pair("18", "Krisesenter"),
            Pair("19", "Asylmottak/ UDI/ innvandringsmyndighet"),
            Pair(
                "20", "Utekontakt/ fritidsklubb"
            ),
            Pair(
                "21",
                "Frivillige organisasjoner/ idrettslag"
            ),
            Pair(
                "22",
                "Andre offentlige instanser (krever presisering)"
            ),
            Pair("23", "Anonym")
        )
            .map { CodeListItem(it.key, it.value, validFrom) }

        @JvmStatic
        fun getCodes(date: LocalDate): List<CodeListItem> =
            TypeUtils.getCodes(date, codeList)

        @JvmStatic
        fun getRandomCode(date: LocalDate): String =
            getCodes(date)
                .filter { item ->
                    !item.description
                        .contains("krever presisering")
                }
                .random().code
    }
}
