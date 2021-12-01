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

        private val codeList = arrayOf(
            arrayOf("1", "Barnet selv"),
            arrayOf("2", "Mor/ far/ foresatte"),
            arrayOf("3", "Familie for øvrig"),
            arrayOf("4", "Andre privatpersoner"),
            arrayOf("5", "Barnvernstjenesten"),
            arrayOf("6", "NAV (kommune og stat)"),
            arrayOf("7", "Barnevernsvakt"),
            arrayOf("8", "Politi"),
            arrayOf("9", "Barnehage"),
            arrayOf("10", "Helsestasjon/skolehelsetjenesten"),
            arrayOf("11", "Skole"),
            arrayOf("12", "Pedagogisk-psykologisk tjeneste (PPT)"),
            arrayOf("13", "Psykisk helsevern for barn og unge (kommune og stat)"),
            arrayOf("14", "Psykisk helsevern for voksne (kommune og stat)"),
            arrayOf("15", "Lege/ sykehus/ tannlege"),
            arrayOf("16", "Familievernkontor"),
            arrayOf("17", "Tjenester og instanser med ansvar for oppfølging av personers rusproblemer (kommune og stat)"),
            arrayOf("18", "Krisesenter"),
            arrayOf("19", "Asylmottak/ UDI/ innvandringsmyndighet"),
            arrayOf("20", "Utekontakt/ fritidsklubb"),
            arrayOf("21", "Frivillige organisasjoner/ idrettslag"),
            arrayOf("22", "Andre offentlige instanser (krever presisering)"),
            arrayOf("23", "Anonym")
        )
            .map { CodeListItem(it[0], it[1], validFrom) }

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
