package no.ssb.barn.xsd

import no.ssb.barn.util.TypeUtils
import java.time.LocalDate
import java.util.*
import javax.xml.bind.annotation.*

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Status", propOrder = ["id", "endretDato", "kode"])
data class VedtakStatusType(
    @field:XmlAttribute(name = "Id", required = true)
    val id: UUID,

    @field:XmlAttribute(name = "EndretDato", required = true)
    @field:XmlSchemaType(name = "date")
    val endretDato: LocalDate,

    @field:XmlAttribute(name = "Kode", required = true)
    val kode: String
) {
    companion object {

        @JvmStatic
        fun getCodes(date: LocalDate): List<CodeListItem> =
            TypeUtils.getCodes(date, codeList)

        private val validFrom: LocalDate = LocalDate.parse("2022-01-01")

        private val codeList =
            mapOf(
                "1" to "Godkjent",
                "2" to "Begjæring oversendt nemnd",
                "3" to "Utgår / Bortfalt etter BVL",
                "4" to "Avslått / Avsluttet"
            )
                .map { CodeListItem(it.key, it.value, validFrom) }
    }
}
