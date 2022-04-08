package no.ssb.barn.xsd

import no.ssb.barn.util.TypeUtils
import java.time.LocalDate
import java.util.*
import javax.xml.bind.annotation.*

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TilsynHyppighetType", propOrder = ["id", "startDato", "kode"])
data class TilsynHyppighetType(
    @field:XmlAttribute(name = "Id", required = true)
    val id: UUID,

    @field:XmlAttribute(name = "StartDato", required = true)
    @field:XmlSchemaType(name = "date")
    val startDato: LocalDate,

    @field:XmlAttribute(name = "Kode", required = true)
    val kode: String
) {
    companion object {

        @JvmStatic
        fun getCodes(date: LocalDate): List<CodeListItem> =
            TypeUtils.getCodes(date, codeList)

        @JvmStatic
        fun getRandomCode(date: LocalDate): String =
            getCodes(date)
                .random().code

        private val validFrom: LocalDate = LocalDate.parse("2022-01-01")

        private val codeList = arrayOf(
            "4" to "4 tilsyn pr. år (standard)",
            "2" to "2 tilsyn pr. år (redusert)"
        )
            .map { CodeListItem(it.first, it.second, validFrom) }
    }
}