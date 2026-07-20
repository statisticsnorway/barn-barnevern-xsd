package no.ssb.barn.xsd.v5

import jakarta.xml.bind.annotation.*
import java.time.LocalDate

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
    name = "FlyttingType", propOrder = ["id", "migrertId", "flyttetDato", "erSlettet", "arsakFra", "flyttingTil"
    ]
)
data class FlyttingType(
    @field:XmlElement(name = "Id", required = true)
    val id: String,

    @field:XmlElement(name = "MigrertId")
    val migrertId: String? = null,

    @field:XmlElement(name = "FlyttetDato", required = true)
    @XmlSchemaType(name = "date")
    val flyttetDato: LocalDate,

    @field:XmlElement(name = "ErSlettet")
    val erSlettet: Boolean? = null,

    @field:XmlElement(name = "ArsakFra", required = true)
    val arsakFra: String,

    @field:XmlElement(name = "FlyttingTil", required = true)
    val flyttingTil: String?
)