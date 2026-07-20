package no.ssb.barn.xsd.v5

import jakarta.xml.bind.annotation.*
import java.time.LocalDateTime

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
    name = "BarnevernregisterInnrapporteringType",
    propOrder = ["id", "datoUttrekk", "forrigeId", "fagsystem", "avgiver", "sak"
    ]
)
data class BarnevernregisterInnrapporteringType(
    @field:XmlElement(name = "Id", required = true)
    val id: String,

    @field:XmlElement(name = "DatoUttrekk", required = true)
    @XmlSchemaType(name = "dateTime")
    val datoUttrekk: LocalDateTime,

    @field:XmlElement(name = "ForrigeId")
    val forrigeId: String? = null,

    @field:XmlElement(name = "Fagsystem", required = true)
    val fagsystem: FagsystemType,

    @field:XmlElement(name = "Avgiver", required = true)
    val avgiver: AvgiverType,

    @field:XmlElement(name = "Sak", required = true)
    val sak: SakType,
)