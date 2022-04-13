package no.ssb.barn.xsd

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty
import no.ssb.barn.util.TypeUtils
import java.time.LocalDate
import java.util.*
import javax.xml.bind.annotation.*

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
    name = "PlanType",
    propOrder = ["id", "migrertId", "startDato", "evaluering", "konklusjon"]
)
data class PlanType(
    @field:XmlAttribute(name = "Id", required = true)
    val id: UUID,

    @field:XmlAttribute(name = "MigrertId")
    val migrertId: String? = null,

    @field:XmlAttribute(name = "StartDato", required = true)
    @field:XmlSchemaType(name = "date")
    val startDato: LocalDate,

    @field:XmlAttribute(name = "Plantype", required = true)
    val plantype: String,

    @field:JacksonXmlProperty(localName = "Evaluering")
    @field:JacksonXmlElementWrapper(useWrapping = false)
    val evaluering: MutableList<PlanEvalueringType> = mutableListOf(),

    @field:XmlElement(name = "Konklusjon")
    val konklusjon: PlanKonklusjonType? = null
) {
    companion object {

        fun getPlantype(date: LocalDate): List<CodeListItem> =
            TypeUtils.getCodes(date, planTypeList)

        private val validFrom: LocalDate = LocalDate.parse("2013-01-01")

        private val planTypeList = mapOf(
            "1" to "Tiltaksplan etter § 4-5",
            "2" to "Tiltaksplan etter § 4-28",
            "3" to "Foreløpig omsorgsplan etter § 4-15, 3. ledd",
            "4" to "Omsorgsplan etter § 4-15, 3. ledd"
        )
            .map {
                CodeListItem(it.key, it.value, validFrom)
            }
    }
}
