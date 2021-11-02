package no.ssb.barn.xsd

import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlAttribute
import javax.xml.bind.annotation.XmlType

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SaksinnholdType", propOrder = ["kode", "presisering"])
data class SaksinnholdType(
    @XmlAttribute(name = "Kode", required = true)
    var kode: String,

    @XmlAttribute(name = "Presisering")
    var presisering: String? = null
) {
    fun getKoder(): List<Pair<String, String>> {
        return listOf(
            Pair("1", "Foreldres somatiske sykdom"),
            Pair("2", "Foreldres psykiske problem/ lidelse"),
            Pair("3", "Foreldres rusmisbruk"),
            Pair("4", "Foreldres manglende foreldreferdigheter"),
            Pair("20", "Foreldres manglende beskyttelse av barnet"),
            Pair("21", "Foreldres manglende stimulering og regulering av barnet"),
            Pair("22", "Foreldres manglende sensitivitet og følelsesmessige tilgjengelighet for barnet"),
            Pair("23", "Foreldres manglende oppfølging av barnets behov for barnehage, skole og pedagogiske tjenester"),
            Pair("24", "Konflikt mellom foreldre som ikke bor sammen"),
            Pair("5", "Foreldres kriminalitet"),
            Pair("6", "Høy grad av konflikt hjemme"),
            Pair("7", "Vold i hjemmet/ barnet vitne til vold i nære relasjoner"),
            Pair("8", "Barnet utsatt for vanskjøtsel (Barnet overlatt til seg selv, dårlig kosthold, dårlig hygiene)"),
            Pair("9", "Barnet utsatt for fysisk vold"),
            Pair("10", "Barnet utsatt for psykisk vold"),
            Pair("11", "Barnet utsatt for seksuelle overgrep"),
            Pair("12", "Barnet mangler omsorgsperson"),
            Pair("13", "Barnet har nedsatt funksjonsevne"),
            Pair("14", "Barnets psykiske problem/lidelse"),
            Pair("15", "Barnets rusmisbruk"),
            Pair("16", "Barnets atferd/ kriminalitet"),
            Pair("17", "Barnets relasjonsvansker (mistanke om eller diagnostiserte tilknytningsvansker," +
                    " problematikk knyttet til samspillet mellom barn og omsorgspersoner) "),
            Pair("25", "Barnets atferd"),
            Pair("26", "Barnets kriminelle handlinger"),
            Pair("27", "Barnet utsatt for menneskehandel"),
            Pair("18", "Andre forhold ved foreldre/ familien (krever presisering)" +
                    " (Denne kategorien skal kunne benyttes dersom ingen av kategoriene 1-27 passer.)"),
            Pair("19", "Andre forhold ved barnets situasjon (krever presisering)" +
                    " (Denne kategorien skal kunne benyttes dersom ingen av kategoriene 1-27 passer.)")
        )
    }

    fun harPresisering(): Boolean {
        return getKoder().filter {pair: Pair<String, String> -> pair.second.contains("krever presisering") }.isNotEmpty()
    }
}
