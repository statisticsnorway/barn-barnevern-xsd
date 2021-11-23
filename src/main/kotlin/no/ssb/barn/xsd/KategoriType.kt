package no.ssb.barn.xsd

import no.ssb.barn.codelists.CodelistItem
import java.time.LocalDate
import jakarta.xml.bind.annotation.XmlAccessType
import jakarta.xml.bind.annotation.XmlAccessorType
import jakarta.xml.bind.annotation.XmlAttribute
import jakarta.xml.bind.annotation.XmlType

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "KategoriType", propOrder = ["kode", "presisering"])
data class KategoriType(
    @field:XmlAttribute(name = "Kode", required = true)
    var kode: String,

    @field:XmlAttribute(name = "Presisering")
    var presisering: String? = null
) {
    companion object {
        @JvmStatic
        fun getCodes(date: LocalDate): List<CodelistItem> {
            return listOf(
                CodelistItem(
                    "1.1",
                    "Barnvernsinstitusjoner <br>" +
                            "(gjelder alle typer barnevernsinstitusjoner)",
                    LocalDate.parse("2013-01-01")
                ),
                CodelistItem(
                    "1.2",
                    "Plassering i institusjon etter annen lov",
                    LocalDate.parse("2013-01-01")
                ),
                CodelistItem(
                    "1.99",
                    "Andre tiltak (krever presisering)",
                    LocalDate.parse("2013-01-01")
                ),
                CodelistItem(
                    "2.1",
                    "Fosterhjem i familie og nære nettverk",
                    LocalDate.parse("2013-01-01")
                ),
                CodelistItem(
                    "2.2",
                    "Fosterhjem utenfor familie og nære nettverk",
                    LocalDate.parse("2013-01-01")
                ),
                CodelistItem(
                    "2.3",
                    "Statlige familiehjem (gjelder fosterhjem som staten har ansvar for)",
                    LocalDate.parse("2013-01-01")
                ),
                CodelistItem(
                    "2.4",
                    "Fosterhjem etter § 4-27",
                    LocalDate.parse("2013-01-01")
                ),
                CodelistItem(
                    "2.5",
                    "Beredskapshjem utenom familie og nære nettverk",
                    LocalDate.parse("2013-01-01")
                ),
                CodelistItem(
                    "2.6",
                    "Midlertidig hjem i familie og nære nettverk",
                    LocalDate.parse("2020-01-01")
                ),
                CodelistItem(
                    "2.99",
                    "Andre tiltak (krever presisering)",
                    LocalDate.parse("2013-01-01")
                ),
                CodelistItem(
                    "3.1",
                    "MST",
                    LocalDate.parse("2013-01-01")
                ),
                CodelistItem(
                    "3.2",
                    "PMTO",
                    LocalDate.parse("2013-01-01")
                ),
                CodelistItem(
                    "3.3",
                    "FFT",
                    LocalDate.parse("2013-01-01")
                ),
                CodelistItem(
                    "3.4",
                    "Weber Stratton",
                    LocalDate.parse("2013-01-01")
                ),
                CodelistItem(
                    "3.5",
                    "ICDP",
                    LocalDate.parse("2013-01-01")
                ),
                CodelistItem(
                    "3.6",
                    "Marte Meo",
                    LocalDate.parse("2013-01-01")
                ),
                CodelistItem(
                    "3.7",
                    "Andre hjemmebaserte tiltak <br>" +
                            "(gjelder andre tiltak i hjemmet som følger en spesiell <br>" +
                            "metodikk og som gjenomføres på en systematisk måte) <br>" +
                            "(krever presisering)",
                    LocalDate.parse("2013-01-01")
                ),
                CodelistItem(
                    "3.8",
                    "Sentre for foreldre og barn",
                    LocalDate.parse("2013-01-01")
                ),
                CodelistItem(
                    "3.9",
                    "Vedtak om råd og veiledning",
                    LocalDate.parse("2013-01-01")
                ),
                CodelistItem(
                    "3.10",
                    "Hjemmekonsulent/miljøarbeider",
                    LocalDate.parse("2013-01-01")
                ),
                CodelistItem(
                    "3.99",
                    "Andre tiltak (krever presisering)",
                    LocalDate.parse("2013-01-01")
                ),
                CodelistItem(
                    "4.1",
                    "Barnehage",
                    LocalDate.parse("2013-01-01")
                ),
                CodelistItem(
                    "4.2",
                    "SFO/ Aktivitetsskole",
                    LocalDate.parse("2013-01-01")
                ),
                CodelistItem(
                    "4.3",
                    "Fritidsaktiviteter",
                    LocalDate.parse("2013-01-01")
                ),
                CodelistItem(
                    "4.4",
                    "Økonomisk hjelp for øvrig <br>" +
                            "(gjelder økonomisk hjelp som ikke inngår i 4.1, 4.2 og 4.3)",
                    LocalDate.parse("2013-01-01")
                ),
                CodelistItem(
                    "4.5",
                    "Besøkshjem/ avlastningstiltak",
                    LocalDate.parse("2013-01-01")
                ),
                CodelistItem(
                    "4.6",
                    "Støttekontakt",
                    LocalDate.parse("2013-01-01")
                ),
                CodelistItem(
                    "4.7",
                    "Samtalegrupper/ barnegrupper <br>" +
                            "(gjelder strukturerte samtale- og aktivitetstilbud for aldersbestemte grupper).",
                    LocalDate.parse("2013-01-01")
                ),
                CodelistItem(
                    "4.8",
                    "Utdanning og arbeid <br>" +
                            "(gjelder oppfølging og støtte for å styrke barn og <br>" +
                            "ungdoms tilknytning til skole eller arbeid)",
                    LocalDate.parse("2013-01-01")
                ),
                CodelistItem(
                    "4.9",
                    "ART",
                    LocalDate.parse("2013-01-01")
                ),
                CodelistItem(
                    "4.99",
                    "Andre tiltak (krever presisering)",
                    LocalDate.parse("2013-01-01")
                ),
                CodelistItem(
                    "5.1",
                    "Frivillig tilsyn i hjemmet <br>" +
                            "(gjelder der foreldre har samtykket til tilsyn)",
                    LocalDate.parse("2013-01-01")
                ),
                CodelistItem(
                    "5.2",
                    "Pålagt tilsyn i hjemmet <br>" +
                            "(gjelder der tilsyn skjer etter vedtak i fylkesnemnd)",
                    LocalDate.parse("2013-01-01")
                ),
                CodelistItem(
                    "5.3",
                    "Tilsyn under samvær",
                    LocalDate.parse("2013-01-01")
                ),
                CodelistItem(
                    "5.4",
                    "Ruskontroll",
                    LocalDate.parse("2013-01-01")
                ),
                CodelistItem(
                    "5.99",
                    "Andre tiltak (krever presisering)",
                    LocalDate.parse("2013-01-01")
                ),
                CodelistItem(
                    "6.1",
                    "Familieråd",
                    LocalDate.parse("2013-01-01")
                ),
                CodelistItem(
                    "6.2",
                    "Nettverksmøter <br>" +
                            "(Strukturerte dialogmøter som består av <br>" +
                            "barneverntjenesten, foreldre og ungdom og andre personer <br>" +
                            "som er viktige i ungdommens liv. Invitasjon av deltakere <br>" +
                            "skjer i samråd mellom barneverntjenesten, ungdom og foreldre)",
                    LocalDate.parse("2013-01-01")
                ),
                CodelistItem(
                    "6.3",
                    "Individuell plan",
                    LocalDate.parse("2013-01-01")
                ),
                CodelistItem(
                    "6.4",
                    "Deltakelse i ansvarsgruppe",
                    LocalDate.parse("2013-01-01")
                ),
                CodelistItem(
                    "6.5",
                    "Samarbeid om utdanning og arbeid",
                    LocalDate.parse("2013-01-01")
                ),
                CodelistItem(
                    "6.99",
                    "Andre tiltak (krever presisering)",
                    LocalDate.parse("2013-01-01")
                ),
                CodelistItem(
                    "7.1",
                    "Bvl § 4-10 medisinsk undersøkelse og behandling",
                    LocalDate.parse("2013-01-01")
                ),
                CodelistItem(
                    "7.2",
                    "Bvl § 4-11 behandling av barn med særlige opplæringsbehov",
                    LocalDate.parse("2013-01-01")
                ),
                CodelistItem(
                    "7.3",
                    "Psykisk helsehjelp for barn og unge <br>" +
                            "(Gjelder ikke ved henvisninger, men når barn får behandling <br>" +
                            "av psykolog eller innen psykisk helsevern/BUP)",
                    LocalDate.parse("2013-01-01")
                ),
                CodelistItem(
                    "7.99",
                    "Andre tiltak (krever presisering)",
                    LocalDate.parse("2013-01-01")
                ),
                CodelistItem(
                    "8.1",
                    "Økonomisk hjelp ved etablering i egen bolig/ hybel (Ikke husleie)",
                    LocalDate.parse("2013-01-01")
                ),
                CodelistItem(
                    "8.2",
                    "Bolig med oppfølging (inkluderer også bofellesskap)",
                    LocalDate.parse("2013-01-01")
                ),
                CodelistItem(
                    "8.3",
                    "Botreningskurs",
                    LocalDate.parse("2013-01-01")
                ),
                CodelistItem(
                    "8.99",
                    "Andre tiltak (krever presisering)",
                    LocalDate.parse("2013-01-01")
                )
            ).filter {
                (date.isEqual(it.validFrom) || date.isAfter(it.validFrom))
                        && (date.isBefore(it.validTo) || date.isEqual(it.validTo))
            }
        }

        fun getTiltakOpphevelse(date: LocalDate): List<CodelistItem> {
            return listOf(
                CodelistItem(
                    "1",
                    "Barnet tilbakeført til foreldre/ familien jamfør § 4-21",
                    LocalDate.parse("2013-01-01")
                ),
                CodelistItem(
                    "2",
                    "Barnet har fylt 18 år",
                    LocalDate.parse("2013-01-01")
                ),
                CodelistItem(
                    "3",
                    "Adopsjon § 4-20",
                    LocalDate.parse("2013-01-01")
                ),
                CodelistItem(
                    "4",
                    "Annet (krever presisering)",
                    LocalDate.parse("2013-01-01")
                )
            ).filter {
                (date.isEqual(it.validFrom) || date.isAfter(it.validFrom))
                        && (date.isBefore(it.validTo) || date.isEqual(it.validTo))
            }
        }

    }
}
