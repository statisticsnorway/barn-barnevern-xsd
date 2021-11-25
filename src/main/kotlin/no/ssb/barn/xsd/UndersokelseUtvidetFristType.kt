package no.ssb.barn.xsd

import no.ssb.barn.codelists.CodeListItem
import no.ssb.barn.converter.LocalDateAdapter
import java.time.LocalDate
import jakarta.xml.bind.annotation.*
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "UtvidetFrist", propOrder = ["startDato", "innvilget"])
data class UndersokelseUtvidetFristType(
    @field:XmlAttribute(name = "StartDato", required = true)
    @field:XmlSchemaType(name = "date")
    @field:XmlJavaTypeAdapter(
        LocalDateAdapter::class
    )
    var startDato: LocalDate = LocalDate.now(),

    @field:XmlAttribute(name = "Innvilget")
    var innvilget: String? = getInnvilget(LocalDate.now())[0].code
) {
    companion object {
        @JvmStatic
        fun getInnvilget(date: LocalDate): List<CodeListItem> {
            return listOf(
                CodeListItem(
                    "1",
                    "Ja",
                    LocalDate.of(2022, 1, 1)
                ),
                CodeListItem(
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

