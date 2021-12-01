package no.ssb.barn.xsd

import jakarta.xml.bind.annotation.*
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter
import no.ssb.barn.codelists.CodeListItem
import no.ssb.barn.converter.LocalDateAdapter
import java.time.LocalDate

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
    name = "UndersokelseKonklusjon",
    propOrder = ["sluttDato", "kode", "presisering"]
)
data class UndersokelseKonklusjonType(
    @field:XmlAttribute(name = "SluttDato", required = true)
    @field:XmlSchemaType(name = "date")
    @field:XmlJavaTypeAdapter(
        LocalDateAdapter::class
    )
    var sluttDato: LocalDate = LocalDate.now(),

    @field:XmlAttribute(name = "Kode", required = true)
    var kode: String = getCodes(LocalDate.now())[0].code,

    @field:XmlAttribute(name = "Presisering")
    var presisering: String? = null
) {
    companion object {
        private val validFrom: LocalDate = LocalDate.parse("2013-01-01")
        private val codeMap =
            mapOf(
                Pair(
                    "1",
                    "Barneverntjenesten fatter vedtak om tiltak"
                ),
                Pair(
                    "2",
                    "Begjæring om tiltak for fylkesnemnda"
                ),
                Pair(
                    "3",
                    "Undersøkelsen henlagt etter barnverntjenestens vurdering " +
                            "(kategorien gjelder når barneverntjenesten vurderer at vilkår " +
                            "for å sette inn tiltak ikke er oppfylt)"
                ),
                Pair(
                    "4",
                    "Undersøkelsen henlagt etter partens ønske " +
                            "(kategorien gjelder når barneverntjenesten vurderer at vilkår for " +
                            "å sette inn tiltak etter § 4-4 er tilstede, men saken henlegges fordi " +
                            "foreldre/barnet ikke samtykker til tiltak. Gjelder bare når det er " +
                            "snakk om hjelpetiltak som foreldre/barn kan takke nei til.)"
                ),
                Pair(
                    "5",
                    "Undersøkelsen henlagt som følge av flytting " +
                            "kategorien gjelder når undersøkelsessak henlegges fordi barnet " +
                            "flytter til en ny kommune. Bør også innebære et underpunkt der " +
                            "barneverntjenesten må oppgi om saken er meldt videre til barnets nye " +
                            "oppholdskommune, obligatorisk ja/nei svar) (krever presisering)"
                ),
                Pair(
                    "6",
                    "Undersøkelsen henlagt etter henvisning til annen instans"
                )
            )
                .map { CodeListItem(it.key, it.value, validFrom) }

        @JvmStatic
        fun getCodes(date: LocalDate): List<CodeListItem> =
            codeMap.filter {
                (date.isEqual(it.validFrom) || date.isAfter(it.validFrom))
                        && (date.isBefore(it.validTo) || date.isEqual(it.validTo))
            }
    }
}
