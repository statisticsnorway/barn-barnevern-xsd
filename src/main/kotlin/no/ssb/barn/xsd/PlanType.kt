package no.ssb.barn.xsd

import no.ssb.barn.codelists.CodelistItem
import no.ssb.barn.converter.LocalDateAdapter
import java.time.LocalDate
import javax.xml.bind.annotation.*
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PlanType", propOrder = ["id", "migrertId", "startDato", "evaluering", "konklusjon"])
data class PlanType(
    @field:XmlAttribute(name = "Id", required = true)
    var id: String,

    @field:XmlAttribute(name = "MigrertId")
    var migrertId: String? = null,

    @field:XmlAttribute(name = "StartDato", required = true)
    @field:XmlSchemaType(name = "date")
    @field:XmlJavaTypeAdapter(
        LocalDateAdapter::class
    )
    var startDato: LocalDate,

    @field:XmlAttribute(name = "Plantype", required = true)
    var plantype: String,

    @field:XmlElement(name = "Evaluering")
    var evaluering: List<PlanEvalueringType?>? = null,

    @field:XmlElement(name = "Konklusjon")
    var konklusjon: PlanKonklusjonType? = null
){
    companion object {
        fun getPlantype(date: LocalDate): List<CodelistItem> {
            return listOf(
                CodelistItem(
                    "1",
                    "Tiltaksplan etter § 4-5",
                    LocalDate.parse("2013-01-01")
                ),
                CodelistItem(
                    "2",
                    "Tiltaksplan etter § 4-28",
                    LocalDate.parse("2013-01-01")
                ),
                CodelistItem(
                    "3",
                    "Foreløpig omsorgsplan etter § 4-15, 3. ledd",
                    LocalDate.parse("2013-01-01")
                ),
                CodelistItem(
                    "4",
                    "Omsorgsplan etter § 4-15, 3. ledd",
                    LocalDate.parse("2013-01-01")
                )
            ).filter { (date.isEqual(it.validFrom) ||  date.isAfter(it.validFrom))
                    && (date.isBefore(it.validTo) || date.isEqual(it.validTo)) }
        }
    }
}
