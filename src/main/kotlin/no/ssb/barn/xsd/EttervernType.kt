package no.ssb.barn.xsd

import java.time.LocalDate
import java.util.*
import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlAttribute
import javax.xml.bind.annotation.XmlElement
import javax.xml.bind.annotation.XmlSchemaType
import javax.xml.bind.annotation.XmlType

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
