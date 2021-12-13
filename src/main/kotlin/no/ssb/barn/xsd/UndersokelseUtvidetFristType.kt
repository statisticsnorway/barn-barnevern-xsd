package no.ssb.barn.xsd

import jakarta.xml.bind.annotation.*
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter
import no.ssb.barn.converter.LocalDateAdapter
import no.ssb.barn.util.TypeUtils
import java.time.LocalDate

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
        private val validFrom = LocalDate.of(2022, 1, 1)
        private val codeList = listOf(
            CodeListItem(
                "1",
                "Ja",
                validFrom
            ),
            CodeListItem(
                "2",
                "Nei",
                validFrom
            )
        )

        @JvmStatic
        fun getInnvilget(date: LocalDate): List<CodeListItem> =
            TypeUtils.getCodes(date, codeList)
    }
}

