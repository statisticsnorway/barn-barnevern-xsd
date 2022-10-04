package no.ssb.barn.xsd

import java.time.LocalDate
import java.util.*
import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlAttribute
import javax.xml.bind.annotation.XmlSchemaType
import javax.xml.bind.annotation.XmlType

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Status", propOrder = ["id", "endretDato", "kode"])
data class VedtakStatusType(
    @field:XmlAttribute(name = "Id", required = true)
    val id: UUID,

    @field:XmlAttribute(name = "EndretDato", required = true)
    @field:XmlSchemaType(name = "date")
    val endretDato: LocalDate,

    @field:XmlAttribute(name = "Kode", required = true)
    val kode: String
)
