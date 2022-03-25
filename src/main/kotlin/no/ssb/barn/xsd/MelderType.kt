package no.ssb.barn.xsd

import no.ssb.barn.util.TypeUtils
import java.time.LocalDate
import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlAttribute
import javax.xml.bind.annotation.XmlType

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
    name = "MelderType",
    propOrder = ["kode", "presisering"]
)
data class MelderType(
    @field:XmlAttribute(name = "Kode", required = true)
    var kode: String? = getCodes(LocalDate.now())
        .take(1)
        .map { it.code }
        .firstOrNull(),

    @field:XmlAttribute(name = "Presisering")
    var presisering: String? = null
) {
    companion object {

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

        private val validFrom: LocalDate = LocalDate.parse("2013-01-01")

        private val codeList = arrayOf(
            "1" to "Barnet selv",
            "2" to "Mor/ far/ foresatte",
            "3" to "Familie for øvrig",
            "4" to "Andre privatpersoner",
            "5" to "Barnvernstjenesten",
            "6" to "NAV (kommune og stat)",
            "7" to "Barnevernsvakt",
            "8" to "Politi",
            "9" to "Barnehage",
            "10" to "Helsestasjon/skolehelsetjenesten",
            "11" to "Skole",
            "12" to "Pedagogisk-psykologisk tjeneste (PPT)",
            "13" to "Psykisk helsevern for barn og unge (kommune og stat)",
            "14" to "Psykisk helsevern for voksne (kommune og stat)",
            "15" to "Lege/ sykehus/ tannlege",
            "16" to "Familievernkontor",
            "17" to "Tjenester og instanser med ansvar for oppfølging av personers rusproblemer (kommune og stat)",
            "18" to "Krisesenter",
            "19" to "Asylmottak/ UDI/ innvandringsmyndighet",
            "20" to "Utekontakt/ fritidsklubb",
            "21" to "Frivillige organisasjoner/ idrettslag",
            "22" to "Andre offentlige instanser (krever presisering)",
            "23" to "Anonym"
        )
            .map { CodeListItem(it.first, it.second, validFrom) }
    }
}
