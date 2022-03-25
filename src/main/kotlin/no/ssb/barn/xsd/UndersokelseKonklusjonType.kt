package no.ssb.barn.xsd

import no.ssb.barn.converter.LocalDateTimeAdapter
import no.ssb.barn.util.TypeUtils
import java.time.LocalDate
import java.time.ZonedDateTime
import javax.xml.bind.annotation.*
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
    name = "UndersokelseKonklusjon",
    propOrder = ["sluttDato", "kode", "presisering"]
)
data class UndersokelseKonklusjonType(
    @field:XmlAttribute(name = "SluttDato", required = true)
    @field:XmlSchemaType(name = "dateTime")
    @field:XmlJavaTypeAdapter(
        LocalDateTimeAdapter::class
    )
    var sluttDato: ZonedDateTime? = null,

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

        private val validFrom: LocalDate = LocalDate.parse("2013-01-01")

        private val codeList =
            mapOf(
                "1" to "Barneverntjenesten fatter vedtak om tiltak",
                "2" to "Begjæring om tiltak for fylkesnemnda",
                "3" to "Undersøkelsen henlagt etter barnverntjenestens vurdering " +
                        "(kategorien gjelder når barneverntjenesten vurderer at vilkår " +
                        "for å sette inn tiltak ikke er oppfylt)",
                "4" to "Undersøkelsen henlagt etter partens ønske " +
                        "(kategorien gjelder når barneverntjenesten vurderer at vilkår for " +
                        "å sette inn tiltak etter § 4-4 er tilstede, men saken henlegges fordi " +
                        "foreldre/barnet ikke samtykker til tiltak. Gjelder bare når det er " +
                        "snakk om hjelpetiltak som foreldre/barn kan takke nei til.)",
                "5" to "Undersøkelsen henlagt som følge av flytting " +
                        "kategorien gjelder når undersøkelsessak henlegges fordi barnet " +
                        "flytter til en ny kommune. Bør også innebære et underpunkt der " +
                        "barneverntjenesten må oppgi om saken er meldt videre til barnets nye " +
                        "oppholdskommune, obligatorisk ja/nei svar) (krever presisering)",
                "6" to "Undersøkelsen henlagt etter henvisning til annen instans"
            )
                .map { CodeListItem(it.key, it.value, validFrom) }
    }
}
