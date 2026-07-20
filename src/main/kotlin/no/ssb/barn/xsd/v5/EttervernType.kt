package no.ssb.barn.xsd.v5

import jakarta.xml.bind.annotation.*
import java.time.LocalDate

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
    name = "EttervernType", propOrder = ["id", "migrertId", "tilbudSendtDato", "erSlettet", "konklusjon"
    ]
)
data class EttervernType(
    @field:XmlElement(name = "Id", required = true)
    val id: String,

    @field:XmlElement(name = "MigrertId")
    val migrertId: String? = null,

    @field:XmlElement(name = "TilbudSendtDato", required = true)
    @XmlSchemaType(name = "date")
    val tilbudSendtDato: LocalDate,

    @field:XmlElement(name = "ErSlettet")
    val erSlettet: Boolean? = null,

    @field:XmlElement(name = "Konklusjon")
    val konklusjon: EttervernKonklusjonType? = null
)