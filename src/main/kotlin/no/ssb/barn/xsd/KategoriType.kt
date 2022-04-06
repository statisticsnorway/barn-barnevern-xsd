package no.ssb.barn.xsd

import no.ssb.barn.util.TypeUtils
import java.time.LocalDate
import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlAttribute
import javax.xml.bind.annotation.XmlType

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "KategoriType", propOrder = ["kode", "presisering"])
data class KategoriType(
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
        fun getTiltakOpphevelse(date: LocalDate): List<CodeListItem> =
            TypeUtils.getCodes(date, tiltakOpphevelseList)

        private const val otherMeasures = "Andre tiltak (krever presisering)"
        private val validFrom: LocalDate = LocalDate.parse("2013-01-01")

        private val codeList =
            mapOf(
                "1.1" to "Barnvernsinstitusjoner <br>" +
                        "(gjelder alle typer barnevernsinstitusjoner)",
                "1.2" to "Plassering i institusjon etter annen lov",
                "1.99" to otherMeasures,
                "2.1" to "Fosterhjem i familie og nære nettverk",
                "2.2" to "Fosterhjem utenfor familie og nære nettverk",
                "2.3" to "Statlige familiehjem (gjelder fosterhjem som staten har ansvar for)",
                "2.4" to "Fosterhjem etter § 4-27",
                "2.5" to "Beredskapshjem utenom familie og nære nettverk",
                "2.6" to "Midlertidig hjem i familie og nære nettverk",
                "2.99" to otherMeasures,
                "3.1" to "MST",
                "3.2" to "PMTO",
                "3.3" to "FFT",
                "3.4" to "Weber Stratton",
                "3.5" to "ICDP",
                "3.6" to "Marte Meo",
                "3.7" to "Andre hjemmebaserte tiltak <br>" +
                        "(gjelder andre tiltak i hjemmet som følger en spesiell <br>" +
                        "metodikk og som gjenomføres på en systematisk måte) <br>" +
                        "(krever presisering)",
                "3.8" to "Sentre for foreldre og barn",
                "3.9" to "Vedtak om råd og veiledning",
                "3.10" to "Hjemmekonsulent/miljøarbeider",
                "3.99" to otherMeasures,
                "4.1" to "Barnehage",
                "4.2" to "SFO/ Aktivitetsskole",
                "4.3" to "Fritidsaktiviteter",
                "4.4" to "Økonomisk hjelp for øvrig <br>(gjelder økonomisk hjelp som ikke inngår i 4.1, 4.2 og 4.3)",
                "4.5" to "Besøkshjem/ avlastningstiltak",
                "4.6" to "Støttekontakt",
                "4.7" to ("Samtalegrupper/ barnegrupper <br>"
                        + "(gjelder strukturerte samtale- og aktivitetstilbud for aldersbestemte grupper)."),
                "4.8" to ("Utdanning og arbeid <br>"
                        + "(gjelder oppfølging og støtte for å styrke barn og <br>"
                        + "ungdoms tilknytning til skole eller arbeid)"),
                "4.9" to "ART",
                "4.99" to otherMeasures,
                "5.1" to ("Frivillig tilsyn i hjemmet <br>"
                        + "(gjelder der foreldre har samtykket til tilsyn)"),
                "5.2" to "Pålagt tilsyn i hjemmet <br>(gjelder der tilsyn skjer etter vedtak i fylkesnemnd)",
                "5.3" to "Tilsyn under samvær",
                "5.4" to "Ruskontroll",
                "5.99" to otherMeasures,
                "6.1" to "Familieråd",
                "6.2" to "Nettverksmøter <br>" +
                        "(Strukturerte dialogmøter som består av <br>" +
                        "barneverntjenesten, foreldre og ungdom og andre personer <br>" +
                        "som er viktige i ungdommens liv. Invitasjon av deltakere <br>" +
                        "skjer i samråd mellom barneverntjenesten, ungdom og foreldre)",
                "6.3" to "Individuell plan",
                "6.4" to "Deltakelse i ansvarsgruppe",
                "6.99" to otherMeasures,
                "7.1" to "Bvl § 4-10 medisinsk undersøkelse og behandling",
                "7.2" to "Bvl § 4-11 behandling av barn med særlige opplæringsbehov",
                "7.3" to "Psykisk helsehjelp for barn og unge <br>" +
                        "(Gjelder ikke ved henvisninger, men når barn får behandling <br>" +
                        "av psykolog eller innen psykisk helsevern/BUP)",
                "7.99" to otherMeasures,
                "8.1" to "Økonomisk hjelp ved etablering i egen bolig/ hybel (Ikke husleie)",
                "8.2" to "Bolig med oppfølging (inkluderer også bofellesskap)",
                "8.3" to "Botreningskurs",
                "8.99" to otherMeasures
            )
                .map {
                    CodeListItem(
                        it.key,
                        it.value,
                        if (it.key == "2.6") LocalDate.parse("2020-01-01") else validFrom
                    )
                }

        private val tiltakOpphevelseList =
            mapOf(
                "1" to "Barnet tilbakeført til foreldre/ familien jamfør § 4-21",
                "2" to "Barnet har fylt 18 år",
                "3" to "Adopsjon § 4-20",
                "4" to "Annet (krever presisering)"
            )
                .map {
                    CodeListItem(it.key, it.value, validFrom)
                }
    }
}
