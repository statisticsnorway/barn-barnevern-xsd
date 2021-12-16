package no.ssb.barn.xsd

import no.ssb.barn.converter.LocalDateAdapter
import no.ssb.barn.converter.UuidAdapter
import no.ssb.barn.util.TypeUtils
import java.time.LocalDate
import java.util.*
import javax.xml.bind.annotation.*
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
    name = "PlanType",
    propOrder = ["id", "migrertId", "startDato", "evaluering", "konklusjon"]
)
data class PlanType(
    @field:XmlAttribute(name = "Id", required = true)
    @field:XmlJavaTypeAdapter(
        UuidAdapter::class
    )
    var id: UUID = UUID.randomUUID(),

    @field:XmlAttribute(name = "MigrertId")
    var migrertId: String? = null,

    @field:XmlAttribute(name = "StartDato", required = true)
    @field:XmlSchemaType(name = "date")
    @field:XmlJavaTypeAdapter(
        LocalDateAdapter::class
    )
    var startDato: LocalDate = LocalDate.now(),

    @field:XmlAttribute(name = "Plantype", required = true)
    var plantype: String? = getPlantype(LocalDate.now())
        .take(1)
        .map { it.code }
        .firstOrNull(),

    @field:XmlElement(name = "Evaluering")
    var evaluering: List<PlanEvalueringType> = mutableListOf(),

    @field:XmlElement(name = "Konklusjon")
    var konklusjon: PlanKonklusjonType? = PlanKonklusjonType()
) {
    companion object {

        fun getPlantype(date: LocalDate): List<CodeListItem> =
            TypeUtils.getCodes(date, planTypeList)

        private val validFrom: LocalDate = LocalDate.parse("2013-01-01")

        private val planTypeList = mapOf(
            Pair(
                "1",
                "Tiltaksplan etter § 4-5"
            ),
            Pair(
                "2",
                "Tiltaksplan etter § 4-28"
            ),
            Pair(
                "3",
                "Foreløpig omsorgsplan etter § 4-15, 3. ledd"
            ),
            Pair(
                "4",
                "Omsorgsplan etter § 4-15, 3. ledd"
            )
        )
            .map {
                CodeListItem(it.key, it.value, validFrom)
            }
    }
}
