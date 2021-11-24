package no.ssb.barn.xsd

import no.ssb.barn.codelists.CodelistItem
import no.ssb.barn.converter.LocalDateAdapter
import java.time.LocalDate
import jakarta.xml.bind.annotation.*
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "UndersokelseKonklusjon", propOrder = ["sluttDato", "kode", "presisering"])
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
        @JvmStatic
        fun getCodes(date: LocalDate): List<CodelistItem> {
            return listOf(
                CodelistItem(
                    "1",
                    "Barneverntjenesten fatter vedtak om tiltak",
                    LocalDate.parse("2013-01-01")
                ),
                CodelistItem(
                    "2",
                    "Begjæring om tiltak for fylkesnemnda",
                    LocalDate.parse("2013-01-01")
                ),
                CodelistItem(
                    "3",
                    "Undersøkelsen henlagt etter barnverntjenestens vurdering " +
                            "(kategorien gjelder når barneverntjenesten vurderer at vilkår " +
                            "for å sette inn tiltak ikke er oppfylt)",
                    LocalDate.parse("2013-01-01")
                ),
                CodelistItem(
                    "4",
                    "Undersøkelsen henlagt etter partens ønske " +
                            "(kategorien gjelder når barneverntjenesten vurderer at vilkår for " +
                            "å sette inn tiltak etter § 4-4 er tilstede, men saken henlegges fordi " +
                            "foreldre/barnet ikke samtykker til tiltak. Gjelder bare når det er " +
                            "snakk om hjelpetiltak som foreldre/barn kan takke nei til.)",
                    LocalDate.parse("2013-01-01")
                ),
                CodelistItem(
                    "5",
                    "Undersøkelsen henlagt som følge av flytting " +
                            "kategorien gjelder når undersøkelsessak henlegges fordi barnet " +
                            "flytter til en ny kommune. Bør også innebære et underpunkt der " +
                            "barneverntjenesten må oppgi om saken er meldt videre til barnets nye " +
                            "oppholdskommune, obligatorisk ja/nei svar) (krever presisering)",
                    LocalDate.parse("2013-01-01")
                ),
                CodelistItem(
                    "6",
                    "Undersøkelsen henlagt etter henvisning til annen instans",
                    LocalDate.parse("2013-01-01")
                )
            ).filter { (date.isEqual(it.validFrom) ||  date.isAfter(it.validFrom))
                    && (date.isBefore(it.validTo) || date.isEqual(it.validTo)) }
        }
    }
}
