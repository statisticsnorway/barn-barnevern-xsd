package no.ssb.barn.xsd

import no.ssb.barn.converter.LocalDateAdapter
import no.ssb.barn.util.TypeUtils
import java.time.LocalDate
import javax.xml.bind.annotation.*
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter

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

        @JvmStatic
        fun getCodes(date: LocalDate): List<CodeListItem> =
            TypeUtils.getCodes(date, codeList)

        private val validFrom = LocalDate.parse("2022-01-01")

        private val codeList =
            mapOf(
                Pair(
                    "1",
                    "Gitt tilbud om tiltak, akseptert"
                ),
                Pair(
                    "2",
                    "Gitt tilbud om tiltak, avslått av bruker grunnet ønske om annet tiltak"
                ),
                Pair(
                    "3",
                    "Ikke lenger tiltak etter BVL, etter brukers ønske"
                )
            )
                .map {
                    CodeListItem(it.key, it.value, validFrom)
                }
    }
}
