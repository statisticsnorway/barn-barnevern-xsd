package no.ssb.barn.xsd

import jakarta.xml.bind.annotation.XmlAccessType
import jakarta.xml.bind.annotation.XmlAccessorType
import jakarta.xml.bind.annotation.XmlAttribute
import jakarta.xml.bind.annotation.XmlElement
import jakarta.xml.bind.annotation.XmlSchemaType
import jakarta.xml.bind.annotation.XmlType
import java.time.LocalDate
import java.util.UUID

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "EttervernType", propOrder = ["id", "migrertId", "tilbudSendtDato", "konklusjon"])
data class EttervernType(
    @field:XmlAttribute(name = "Id", required = true)
    val id: UUID,

    @field:XmlAttribute(name = "MigrertId")
    val migrertId: String? = null,

    @field:XmlAttribute(name = "TilbudSendtDato", required = true)
    @field:XmlSchemaType(name = "date")
    val tilbudSendtDato: LocalDate,

    @field:XmlElement(name = "Konklusjon")
    val konklusjon: EttervernKonklusjonType? = null
)
