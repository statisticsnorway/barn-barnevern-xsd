package no.ssb.barn.xsd

import no.ssb.barn.codelists.CodelistItem
import no.ssb.barn.converter.LocalDateAdapter
import java.time.LocalDate
import javax.xml.bind.annotation.*
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "UtvidetFrist", propOrder = ["startDato", "innvilget"])
data class UndersokelseUtvidetFristType(
    @field:XmlAttribute(name = "StartDato", required = true)
    @field:XmlSchemaType(name = "date")
    @field:XmlJavaTypeAdapter(
        LocalDateAdapter::class
    )
    var startDato: LocalDate,

    @field:XmlAttribute(name = "Innvilget")
    var innvilget: String? = null
) {
    companion object {
        @JvmStatic
        fun getInnvilget(date: LocalDate): List<CodelistItem> {
            return listOf(
                CodelistItem(
                    "1",
                    "Ja",
                    LocalDate.of(2022, 1, 1)
                ),
                CodelistItem(
                    "2",
                    "Nei",
                    LocalDate.of(2022, 1, 1)
                )
            ).filter {
                (date.isEqual(it.validFrom) || date.isAfter(it.validFrom))
                        && (date.isBefore(it.validTo) || date.isEqual(it.validTo))
            }
        }
    }
}

