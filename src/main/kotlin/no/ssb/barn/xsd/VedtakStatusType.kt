package no.ssb.barn.xsd

import no.ssb.barn.codelists.CodelistItem
import java.time.LocalDate
import javax.xml.bind.annotation.*

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Status", propOrder = ["endretDato", "kode"])
data class VedtakStatusType(
        @field:XmlAttribute(name = "EndretDato", required = true)
        @field:XmlSchemaType(name = "anySimpleType")
        var endretDato: String? = null,

        @field:XmlAttribute(name = "Kode", required = true)
        var kode: String? = null
){
        companion object {
                @JvmStatic
                fun getCodes(date: LocalDate): List<CodelistItem> {
                        return listOf(
                                CodelistItem(
                                        "1",
                                        "Godkjent",
                                        LocalDate.parse("2022-01-01")
                                ),
                                CodelistItem(
                                        "2",
                                        "Begjæring oversendt nemnd",
                                        LocalDate.parse("2022-01-01")
                                ),
                                CodelistItem(
                                        "3",
                                        "Utgår / Bortfalt etter BVL",
                                        LocalDate.parse("2022-01-01")
                                ),
                                CodelistItem(
                                        "4",
                                        "Avslått / Avsluttet",
                                        LocalDate.parse("2022-01-01")
                                )
                        ).filter { (date.isEqual(it.validFrom) ||  date.isAfter(it.validFrom))
                                && (date.isBefore(it.validTo) || date.isEqual(it.validTo)) }
                }
        }
}
