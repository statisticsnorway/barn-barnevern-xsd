package no.ssb.barn.xsd

import no.ssb.barn.codelists.CodelistItem
import no.ssb.barn.converter.LocalDateAdapter
import no.ssb.barn.generator.RandomGenerator
import java.time.LocalDate
import jakarta.xml.bind.annotation.*
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Konklusjon", propOrder = ["sluttDato", "kode"], factoryMethod = "createMeldingKonklusjonType")
data class MeldingKonklusjonType(
    @field:XmlAttribute(name = "SluttDato")
    @field:XmlSchemaType(name = "date")
    @field:XmlJavaTypeAdapter(
        LocalDateAdapter::class
    )
    var sluttDato: LocalDate? = null,

    @field:XmlAttribute(name = "Kode", required = true)
    var kode: String
){
    companion object {
        @JvmStatic
        fun createMeldingKonklusjonType(): MeldingKonklusjonType {
            return MeldingKonklusjonType(
                LocalDate.now(),
                "1"
            )
        }

        @JvmStatic
        fun getCodes(date: LocalDate): List<CodelistItem> {
            return listOf(
                CodelistItem(
                    "1",
                    "Henlagt",
                    LocalDate.of(2013, 1, 1)
                ),
                CodelistItem(
                    "2",
                    "Ikke henlagt – konklusjonsdato melding (eventuelt 7 dager etter mottatt melding) er startdato undersøkelse",
                    LocalDate.of(2013, 1, 1)
                ),
                CodelistItem(
                    "3",
                    "Henlagt pga. aktive tiltak",
                    LocalDate.of(2013, 1, 1)
                ),
                CodelistItem(
                    "4",
                    "Melding i pågående undersøkelse",
                    LocalDate.of(2013, 1, 1)
                )
            ).filter {
                (date.isEqual(it.validFrom) || date.isAfter(it.validFrom))
                        && (date.isBefore(it.validTo) || date.isEqual(it.validTo))
            }
        }
    }
}
