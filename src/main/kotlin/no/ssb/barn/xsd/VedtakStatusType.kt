package no.ssb.barn.xsd

import no.ssb.barn.converter.LocalDateTimeAdapter
import no.ssb.barn.util.TypeUtils
import java.time.LocalDate
import java.time.ZonedDateTime
import javax.xml.bind.annotation.*
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Status", propOrder = ["endretDato", "kode"])
data class VedtakStatusType(
    @field:XmlAttribute(name = "EndretDato", required = true)
    @field:XmlSchemaType(name = "dateTime")
    @field:XmlJavaTypeAdapter(
        LocalDateTimeAdapter::class
    )
    var endretDato: ZonedDateTime? = null,

    @field:XmlAttribute(name = "Kode", required = true)
    var kode: String? = getCodes(LocalDate.of(2022, 1, 1))
        .take(1)
        .map { it.code }
        .firstOrNull()
) {
    companion object {

        @JvmStatic
        fun getCodes(date: LocalDate): List<CodeListItem> =
            TypeUtils.getCodes(date, codeList)

        private val validFrom: LocalDate = LocalDate.parse("2022-01-01")

        private val codeList =
            mapOf(
                Pair(
                    "1",
                    "Godkjent"
                ),
                Pair(
                    "2",
                    "Begjæring oversendt nemnd"
                ),
                Pair(
                    "3",
                    "Utgår / Bortfalt etter BVL"
                ),
                Pair(
                    "4",
                    "Avslått / Avsluttet"
                )
            )
                .map { CodeListItem(it.key, it.value, validFrom) }
    }
}
