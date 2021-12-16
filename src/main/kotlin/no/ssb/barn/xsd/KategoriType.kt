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
        fun getTiltakOpphevelse(date: LocalDate): List<CodeListItem> =
            TypeUtils.getCodes(date, tiltakOpphevelseList)

        private const val otherMeasures = "Andre tiltak (krever presisering)"
        private val validFrom: LocalDate = LocalDate.parse("2013-01-01")

        private val codeList =
            mapOf(
                Pair(
                    "1.1",
                    "Barnvernsinstitusjoner <br>" +
                            "(gjelder alle typer barnevernsinstitusjoner)"
                ),
                Pair("1.2", "Plassering i institusjon etter annen lov"),
                Pair("1.99", otherMeasures),
                Pair("2.1", "Fosterhjem i familie og nære nettverk"),
                Pair("2.2", "Fosterhjem utenfor familie og nære nettverk"),
                Pair("2.3", "Statlige familiehjem (gjelder fosterhjem som staten har ansvar for)"),
                Pair("2.4", "Fosterhjem etter § 4-27"),
                Pair("2.5", "Beredskapshjem utenom familie og nære nettverk"),
                Pair("2.6", "Midlertidig hjem i familie og nære nettverk"),
                Pair("2.99", otherMeasures),
                Pair("3.1", "MST"),
                Pair("3.2", "PMTO"),
                Pair("3.3", "FFT"),
                Pair("3.4", "Weber Stratton"),
                Pair("3.5", "ICDP"),
                Pair("3.6", "Marte Meo"),
                Pair("3.7", "Andre hjemmebaserte tiltak <br>" +
                            "(gjelder andre tiltak i hjemmet som følger en spesiell <br>" +
                            "metodikk og som gjenomføres på en systematisk måte) <br>" +
                            "(krever presisering)"),
                Pair("3.8", "Sentre for foreldre og barn"),
                Pair("3.9", "Vedtak om råd og veiledning"),
                Pair("3.10", "Hjemmekonsulent/miljøarbeider"),
                Pair("3.99", otherMeasures),
                Pair("4.1", "Barnehage"),
                Pair("4.2", "SFO/ Aktivitetsskole"),
                Pair("4.3", "Fritidsaktiviteter"),
                Pair("4.4", "Økonomisk hjelp for øvrig <br>(gjelder økonomisk hjelp som ikke inngår i 4.1, 4.2 og 4.3)"),
                Pair("4.5", "Besøkshjem/ avlastningstiltak"),
                Pair("4.6", "Støttekontakt"),
                Pair("4.7", "Samtalegrupper/ barnegrupper <br>"
                        + "(gjelder strukturerte samtale- og aktivitetstilbud for aldersbestemte grupper)."),
                Pair("4.8", "Utdanning og arbeid <br>"
                            + "(gjelder oppfølging og støtte for å styrke barn og <br>"
                            + "ungdoms tilknytning til skole eller arbeid)"),
                Pair("4.9", "ART"),
                Pair("4.99", otherMeasures),
                Pair("5.1", "Frivillig tilsyn i hjemmet <br>"
                            + "(gjelder der foreldre har samtykket til tilsyn)"),
                Pair("5.2", "Pålagt tilsyn i hjemmet <br>(gjelder der tilsyn skjer etter vedtak i fylkesnemnd)"),
                Pair("5.3", "Tilsyn under samvær"),
                Pair("5.4", "Ruskontroll"),
                Pair("5.99", otherMeasures),
                Pair("6.1", "Familieråd"),
                Pair("6.2", "Nettverksmøter <br>" +
                            "(Strukturerte dialogmøter som består av <br>" +
                            "barneverntjenesten, foreldre og ungdom og andre personer <br>" +
                            "som er viktige i ungdommens liv. Invitasjon av deltakere <br>" +
                            "skjer i samråd mellom barneverntjenesten, ungdom og foreldre)"),
                Pair("6.3", "Individuell plan"),
                Pair("6.4", "Deltakelse i ansvarsgruppe"),
                Pair("6.5", "Samarbeid om utdanning og arbeid"),
                Pair("6.99", otherMeasures),
                Pair("7.1", "Bvl § 4-10 medisinsk undersøkelse og behandling"),
                Pair("7.2", "Bvl § 4-11 behandling av barn med særlige opplæringsbehov"),
                Pair("7.3", "Psykisk helsehjelp for barn og unge <br>" +
                            "(Gjelder ikke ved henvisninger, men når barn får behandling <br>" +
                            "av psykolog eller innen psykisk helsevern/BUP)"),
                Pair("7.99", otherMeasures),
                Pair("8.1", "Økonomisk hjelp ved etablering i egen bolig/ hybel (Ikke husleie)"),
                Pair("8.2", "Bolig med oppfølging (inkluderer også bofellesskap)"),
                Pair("8.3", "Botreningskurs"),
                Pair("8.99", otherMeasures)
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
                Pair("1", "Barnet tilbakeført til foreldre/ familien jamfør § 4-21"),
                Pair("2", "Barnet har fylt 18 år"),
                Pair("3", "Adopsjon § 4-20",),
                Pair("4", "Annet (krever presisering)",)
            )
                .map {
                    CodeListItem(it.key, it.value, validFrom)
                }
    }
}
