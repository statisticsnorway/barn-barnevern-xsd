package no.ssb.barn.xsd

import no.ssb.barn.util.TypeUtils
import java.time.LocalDate
import javax.xml.bind.annotation.*

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
    name = "MeldingKonklusjon",
    propOrder = ["sluttDato", "kode"]
)
data class MeldingKonklusjonType(
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

        private val validFrom: LocalDate = LocalDate.parse("2013-01-01")

        private val codeList = mapOf(
            "1" to "Henlagt",
            "2" to "Ikke henlagt – konklusjonsdato melding (eventuelt 7 dager etter mottatt melding) er startdato undersøkelse",
            "3" to "Henlagt pga. aktive tiltak",
            "4" to "Melding i pågående undersøkelse"
        )
            .map { CodeListItem(it.key, it.value, validFrom) }
    }
}
