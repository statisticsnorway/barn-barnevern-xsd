package no.ssb.barn.xsd

import jakarta.xml.bind.annotation.*
import java.time.LocalDate
import java.util.UUID

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "EttervernType", propOrder = ["id", "migrertId", "tilbudSendtDato", "erSlettet", "konklusjon"])
data class EttervernType(
    @field:XmlAttribute(name = "Id", required = true)
    val id: UUID,

    @field:XmlAttribute(name = "MigrertId")
    val migrertId: String? = null,

    @field:XmlAttribute(name = "TilbudSendtDato", required = true)
    @field:XmlSchemaType(name = "date")
    val tilbudSendtDato: LocalDate,

    @field:XmlAttribute(name = "ErSlettet")
    val erSlettet: Boolean = false,

    @field:XmlElement(name = "Konklusjon")
    val konklusjon: EttervernKonklusjonType? = null
)
