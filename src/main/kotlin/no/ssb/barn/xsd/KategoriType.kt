package no.ssb.barn.xsd

import jakarta.xml.bind.annotation.XmlAccessType
import jakarta.xml.bind.annotation.XmlAccessorType
import jakarta.xml.bind.annotation.XmlAttribute
import jakarta.xml.bind.annotation.XmlType
import no.ssb.barn.codelists.CodeListItem
import java.time.LocalDate

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "KategoriType", propOrder = ["kode", "presisering"])
data class KategoriType(
    @field:XmlAttribute(name = "Kode", required = true)
    var kode: String = getCodes(LocalDate.now())[0].code,

    @field:XmlAttribute(name = "Presisering")
    var presisering: String? = null
) {
    companion object {
        private val validFrom: LocalDate = LocalDate.parse("2013-01-01")

        @JvmStatic
        fun getCodes(date: LocalDate): List<CodeListItem> {
            return listOf(
                CodeListItem(
                    "1.1",
                    "Barnvernsinstitusjoner <br>" +
                            "(gjelder alle typer barnevernsinstitusjoner)",
                    validFrom
                ),
                CodeListItem(
                    "1.2",
                    "Plassering i institusjon etter annen lov",
                    validFrom
                ),
                CodeListItem(
                    "1.99",
                    "Andre tiltak (krever presisering)",
                    validFrom
                ),
                CodeListItem(
                    "2.1",
                    "Fosterhjem i familie og nære nettverk",
                    validFrom
                ),
                CodeListItem(
                    "2.2",
                    "Fosterhjem utenfor familie og nære nettverk",
                    validFrom
                ),
                CodeListItem(
                    "2.3",
                    "Statlige familiehjem (gjelder fosterhjem som staten har ansvar for)",
                    validFrom
                ),
                CodeListItem(
                    "2.4",
                    "Fosterhjem etter § 4-27",
                    validFrom
                ),
                CodeListItem(
                    "2.5",
                    "Beredskapshjem utenom familie og nære nettverk",
                    validFrom
                ),
                CodeListItem( // TODO Jon Ole: Er "2020-01-01" riktig dato når alle de andre er "2013-01-01"?
                    "2.6",
                    "Midlertidig hjem i familie og nære nettverk",
                    LocalDate.parse("2020-01-01")
                ),
                CodeListItem(
                    "2.99",
                    "Andre tiltak (krever presisering)",
                    validFrom
                ),
                CodeListItem(
                    "3.1",
                    "MST",
                    validFrom
                ),
                CodeListItem(
                    "3.2",
                    "PMTO",
                    validFrom
                ),
                CodeListItem(
                    "3.3",
                    "FFT",
                    validFrom
                ),
                CodeListItem(
                    "3.4",
                    "Weber Stratton",
                    validFrom
                ),
                CodeListItem(
                    "3.5",
                    "ICDP",
                    validFrom
                ),
                CodeListItem(
                    "3.6",
                    "Marte Meo",
                    validFrom
                ),
                CodeListItem(
                    "3.7",
                    "Andre hjemmebaserte tiltak <br>" +
                            "(gjelder andre tiltak i hjemmet som følger en spesiell <br>" +
                            "metodikk og som gjenomføres på en systematisk måte) <br>" +
                            "(krever presisering)",
                    validFrom
                ),
                CodeListItem(
                    "3.8",
                    "Sentre for foreldre og barn",
                    validFrom
                ),
                CodeListItem(
                    "3.9",
                    "Vedtak om råd og veiledning",
                    validFrom
                ),
                CodeListItem(
                    "3.10",
                    "Hjemmekonsulent/miljøarbeider",
                    validFrom
                ),
                CodeListItem(
                    "3.99",
                    "Andre tiltak (krever presisering)",
                    validFrom
                ),
                CodeListItem(
                    "4.1",
                    "Barnehage",
                    validFrom
                ),
                CodeListItem(
                    "4.2",
                    "SFO/ Aktivitetsskole",
                    validFrom
                ),
                CodeListItem(
                    "4.3",
                    "Fritidsaktiviteter",
                    validFrom
                ),
                CodeListItem(
                    "4.4",
                    "Økonomisk hjelp for øvrig <br>" +
                            "(gjelder økonomisk hjelp som ikke inngår i 4.1, 4.2 og 4.3)",
                    validFrom
                ),
                CodeListItem(
                    "4.5",
                    "Besøkshjem/ avlastningstiltak",
                    validFrom
                ),
                CodeListItem(
                    "4.6",
                    "Støttekontakt",
                    validFrom
                ),
                CodeListItem(
                    "4.7",
                    "Samtalegrupper/ barnegrupper <br>" +
                            "(gjelder strukturerte samtale- og aktivitetstilbud for aldersbestemte grupper).",
                    validFrom
                ),
                CodeListItem(
                    "4.8",
                    "Utdanning og arbeid <br>" +
                            "(gjelder oppfølging og støtte for å styrke barn og <br>" +
                            "ungdoms tilknytning til skole eller arbeid)",
                    validFrom
                ),
                CodeListItem(
                    "4.9",
                    "ART",
                    validFrom
                ),
                CodeListItem(
                    "4.99",
                    "Andre tiltak (krever presisering)",
                    validFrom
                ),
                CodeListItem(
                    "5.1",
                    "Frivillig tilsyn i hjemmet <br>" +
                            "(gjelder der foreldre har samtykket til tilsyn)",
                    validFrom
                ),
                CodeListItem(
                    "5.2",
                    "Pålagt tilsyn i hjemmet <br>" +
                            "(gjelder der tilsyn skjer etter vedtak i fylkesnemnd)",
                    validFrom
                ),
                CodeListItem(
                    "5.3",
                    "Tilsyn under samvær",
                    validFrom
                ),
                CodeListItem(
                    "5.4",
                    "Ruskontroll",
                    validFrom
                ),
                CodeListItem(
                    "5.99",
                    "Andre tiltak (krever presisering)",
                    validFrom
                ),
                CodeListItem(
                    "6.1",
                    "Familieråd",
                    validFrom
                ),
                CodeListItem(
                    "6.2",
                    "Nettverksmøter <br>" +
                            "(Strukturerte dialogmøter som består av <br>" +
                            "barneverntjenesten, foreldre og ungdom og andre personer <br>" +
                            "som er viktige i ungdommens liv. Invitasjon av deltakere <br>" +
                            "skjer i samråd mellom barneverntjenesten, ungdom og foreldre)",
                    validFrom
                ),
                CodeListItem(
                    "6.3",
                    "Individuell plan",
                    validFrom
                ),
                CodeListItem(
                    "6.4",
                    "Deltakelse i ansvarsgruppe",
                    validFrom
                ),
                CodeListItem(
                    "6.5",
                    "Samarbeid om utdanning og arbeid",
                    validFrom
                ),
                CodeListItem(
                    "6.99",
                    "Andre tiltak (krever presisering)",
                    validFrom
                ),
                CodeListItem(
                    "7.1",
                    "Bvl § 4-10 medisinsk undersøkelse og behandling",
                    validFrom
                ),
                CodeListItem(
                    "7.2",
                    "Bvl § 4-11 behandling av barn med særlige opplæringsbehov",
                    validFrom
                ),
                CodeListItem(
                    "7.3",
                    "Psykisk helsehjelp for barn og unge <br>" +
                            "(Gjelder ikke ved henvisninger, men når barn får behandling <br>" +
                            "av psykolog eller innen psykisk helsevern/BUP)",
                    validFrom
                ),
                CodeListItem(
                    "7.99",
                    "Andre tiltak (krever presisering)",
                    validFrom
                ),
                CodeListItem(
                    "8.1",
                    "Økonomisk hjelp ved etablering i egen bolig/ hybel (Ikke husleie)",
                    validFrom
                ),
                CodeListItem(
                    "8.2",
                    "Bolig med oppfølging (inkluderer også bofellesskap)",
                    validFrom
                ),
                CodeListItem(
                    "8.3",
                    "Botreningskurs",
                    validFrom
                ),
                CodeListItem(
                    "8.99",
                    "Andre tiltak (krever presisering)",
                    validFrom
                )
            ).filter {
                (date.isEqual(it.validFrom) || date.isAfter(it.validFrom))
                        && (date.isBefore(it.validTo) || date.isEqual(it.validTo))
            }
        }

        fun getTiltakOpphevelse(date: LocalDate): List<CodeListItem> {
            return listOf(
                CodeListItem(
                    "1",
                    "Barnet tilbakeført til foreldre/ familien jamfør § 4-21",
                    validFrom
                ),
                CodeListItem(
                    "2",
                    "Barnet har fylt 18 år",
                    validFrom
                ),
                CodeListItem(
                    "3",
                    "Adopsjon § 4-20",
                    validFrom
                ),
                CodeListItem(
                    "4",
                    "Annet (krever presisering)",
                    validFrom
                )
            ).filter {
                (date.isEqual(it.validFrom) || date.isAfter(it.validFrom))
                        && (date.isBefore(it.validTo) || date.isEqual(it.validTo))
            }
        }

    }
}
