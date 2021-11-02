package no.ssb.barn.xsd

import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlAttribute
import javax.xml.bind.annotation.XmlType

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MelderType", propOrder = ["kode", "presisering"])
data class MelderType(
    @XmlAttribute(name = "Kode", required = true)
    var kode: List<String?>,

    @XmlAttribute(name = "Presisering")
    var presisering: String? = null
) {

    fun getKoder(): Array<Pair<String, String>> {
        return arrayOf(
            Pair("1", "Barnet selv"),
            Pair("2", "Mor/ far/ foresatte"),
            Pair("3", "Familie for øvrig"),
            Pair("4", "Andre privatpersoner"),
            Pair("5", "Barnvernstjenesten"),
            Pair("6", "NAV (kommune og stat)"),
            Pair("7", "Barnevernsvakt"),
            Pair("8", "Politi"),
            Pair("9", "Barnehage"),
            Pair("10", "Helsestasjon/skolehelsetjenesten"),
            Pair("11", "Skole"),
            Pair("12", "Pedagogisk-psykologisk tjeneste (PPT)"),
            Pair("13", "Psykisk helsevern for barn og unge (kommune og stat)"),
            Pair("14", "Psykisk helsevern for voksne (kommune og stat)"),
            Pair("15", "Lege/ sykehus/ tannlege"),
            Pair("16", "Familievernkontor"),
            Pair("17", "Tjenester og instanser med ansvar for oppfølging av personers rusproblemer (kommune og stat)"),
            Pair("18", "Krisesenter"),
            Pair("19", "Asylmottak/ UDI/ innvandringsmyndighet"),
            Pair("20", "Utekontakt/ fritidsklubb"),
            Pair("21", "Frivillige organisasjoner/ idrettslag"),
            Pair("22", "Andre offentlige instanser (krever presisering)"),
            Pair("23", "Anonym")
        )
    }
}
