package no.ssb.barn.xsd

import no.ssb.barn.util.TypeUtils
import java.time.LocalDate
import javax.xml.bind.annotation.*

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "EttervernKonklusjon", propOrder = ["sluttDato", "kode"])
data class EttervernKonklusjonType(
    @field:XmlAttribute(name = "SluttDato", required = true)
    @field:XmlSchemaType(name = "date")
    val sluttDato: LocalDate,

    @field:XmlAttribute(name = "Kode", required = true)
    val kode: String
) {
    companion object {

        @JvmStatic
        fun getCodes(date: LocalDate): List<CodeListItem> =
            TypeUtils.getCodes(date, codeList)

        private val validFrom = LocalDate.parse("2022-01-01")

        private val codeList =
            mapOf(
                "1" to "Gitt tilbud om tiltak, akseptert",
                "2" to "Gitt tilbud om tiltak, avslått av bruker grunnet ønske om annet tiltak",
                "3" to "Ikke lenger tiltak etter BVL, etter brukers ønske"
            )
                .map {
                    CodeListItem(it.key, it.value, validFrom)
                }
    }
}
