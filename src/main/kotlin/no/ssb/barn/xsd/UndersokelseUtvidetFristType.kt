package no.ssb.barn.xsd

import no.ssb.barn.util.TypeUtils
import java.time.LocalDate
import javax.xml.bind.annotation.*

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "UtvidetFrist", propOrder = ["startDato", "innvilget"])
data class UndersokelseUtvidetFristType(
    @field:XmlAttribute(name = "StartDato", required = true)
    @field:XmlSchemaType(name = "date")
    val startDato: LocalDate,

    @field:XmlAttribute(name = "Innvilget")
    val innvilget: String?
) {
    companion object {

        @JvmStatic
        fun getInnvilget(date: LocalDate): List<CodeListItem> =
            TypeUtils.getCodes(date, codeList)

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
    }
}

