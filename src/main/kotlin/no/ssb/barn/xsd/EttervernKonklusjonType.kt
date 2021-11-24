package no.ssb.barn.xsd

import no.ssb.barn.codelists.CodelistItem
import no.ssb.barn.converter.LocalDateAdapter
import java.time.LocalDate
import jakarta.xml.bind.annotation.*
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter

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
    var kode: String = getCodes(LocalDate.of(2022, 1, 1 ))[0].code
){
    companion object {
        @JvmStatic
        fun getCodes(date: LocalDate): List<CodelistItem> {
            return listOf(
                CodelistItem(
                    "1",
                    "Gitt tilbud om tiltak, akseptert",
                    LocalDate.parse("2022-01-01")
                ),
                CodelistItem(
                    "2",
                    "Gitt tilbud om tiltak, avslått av bruker grunnet ønske om annet tiltak",
                    LocalDate.parse("2022-01-01")
                ),
                CodelistItem(
                    "3",
                    "Ikke lenger tiltak etter BVL, etter brukers ønske",
                    LocalDate.parse("2022-01-01")
                )
            ).filter { (date.isEqual(it.validFrom) ||  date.isAfter(it.validFrom))
                    && (date.isBefore(it.validTo) || date.isEqual(it.validTo)) }
        }
    }
}
