package no.ssb.barn.xsd

import jakarta.xml.bind.annotation.*
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter
import no.ssb.barn.codelists.CodeListItem
import no.ssb.barn.converter.LocalDateAdapter
import java.time.LocalDate

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "EttervernKonklusjon", propOrder = ["sluttDato", "kode"])
data class EttervernKonklusjonType(
    @field:XmlAttribute(name = "SluttDato")
    @field:XmlSchemaType(name = "date")
    @field:XmlJavaTypeAdapter(
        LocalDateAdapter::class
    )
    var sluttDato: LocalDate? = LocalDate.now(),

    @field:XmlAttribute(name = "Kode", required = true)
    var kode: String = getCodes(LocalDate.of(2022, 1, 1))[0].code
) {
    companion object {
        private val validFrom = LocalDate.parse("2022-01-01")

        @JvmStatic
        fun getCodes(date: LocalDate): List<CodeListItem> {
            return listOf(
                CodeListItem(
                    "1",
                    "Gitt tilbud om tiltak, akseptert",
                    validFrom = validFrom
                ),
                CodeListItem(
                    "2",
                    "Gitt tilbud om tiltak, avslått av bruker grunnet ønske om annet tiltak",
                    validFrom = validFrom
                ),
                CodeListItem(
                    "3",
                    "Ikke lenger tiltak etter BVL, etter brukers ønske",
                    validFrom = validFrom
                )
            ).filter {
                (date.isEqual(it.validFrom) || date.isAfter(it.validFrom))
                        && (date.isBefore(it.validTo) || date.isEqual(it.validTo))
            }
        }
    }
}
