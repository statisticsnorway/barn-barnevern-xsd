package no.ssb.barn.xsd

import no.ssb.barn.codelists.CodeListItem
import no.ssb.barn.converter.LocalDateAdapter
import java.time.LocalDate
import jakarta.xml.bind.annotation.*
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter
import no.ssb.barn.generator.RandomGenerator

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PlanType", propOrder = ["id", "migrertId", "startDato", "evaluering", "konklusjon"])
data class PlanType(
    @field:XmlAttribute(name = "Id", required = true)
    var id: String = RandomGenerator.generateRandomString(10),

    @field:XmlAttribute(name = "MigrertId")
    var migrertId: String? = null,

    @field:XmlAttribute(name = "StartDato", required = true)
    @field:XmlSchemaType(name = "date")
    @field:XmlJavaTypeAdapter(
        LocalDateAdapter::class
    )
    var startDato: LocalDate = LocalDate.now(),

    @field:XmlAttribute(name = "Plantype", required = true)
    var plantype: String = getPlantype(LocalDate.now())[0].code,

    @field:XmlElement(name = "Evaluering")
    var evaluering: MutableList<PlanEvalueringType?>? = MutableList(1) { PlanEvalueringType() },

    @field:XmlElement(name = "Konklusjon")
    var konklusjon: PlanKonklusjonType? = PlanKonklusjonType()
){
    companion object {
        fun getPlantype(date: LocalDate): List<CodeListItem> {
            return listOf(
                CodeListItem(
                    "1",
                    "Tiltaksplan etter § 4-5",
                    LocalDate.parse("2013-01-01")
                ),
                CodeListItem(
                    "2",
                    "Tiltaksplan etter § 4-28",
                    LocalDate.parse("2013-01-01")
                ),
                CodeListItem(
                    "3",
                    "Foreløpig omsorgsplan etter § 4-15, 3. ledd",
                    LocalDate.parse("2013-01-01")
                ),
                CodeListItem(
                    "4",
                    "Omsorgsplan etter § 4-15, 3. ledd",
                    LocalDate.parse("2013-01-01")
                )
            ).filter { (date.isEqual(it.validFrom) ||  date.isAfter(it.validFrom))
                    && (date.isBefore(it.validTo) || date.isEqual(it.validTo)) }
        }
    }
}
