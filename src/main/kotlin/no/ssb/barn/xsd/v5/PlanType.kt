package no.ssb.barn.xsd.v5

import jakarta.xml.bind.annotation.*
import java.time.LocalDate
import java.util.UUID

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
    name = "PlanType", propOrder = ["id", "migrertId", "startDato", "erSlettet", "sluttDato", "kode"]
)
data class PlanType(
    @field:XmlElement(name = "Id", required = true)
    val id: UUID,

    @field:XmlElement(name = "MigrertId")
    val migrertId: String? = null,

    @field:XmlElement(name = "StartDato", required = true)
    @XmlSchemaType(name = "date")
    val startDato: LocalDate,

    @field:XmlElement(name = "ErSlettet")
    val erSlettet: Boolean = false,

    @field:XmlElement(name = "SluttDato")
    @XmlSchemaType(name = "date")
    val sluttDato: LocalDate? = null,

    @field:XmlElement(name = "Kode", required = true)
    val kode: String
)