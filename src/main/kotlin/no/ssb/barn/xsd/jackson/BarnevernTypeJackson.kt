package no.ssb.barn.xsd.jackson

import no.ssb.barn.xsd.AvgiverType
import no.ssb.barn.xsd.FagsystemType
import no.ssb.barn.xsd.SakType
import java.time.ZonedDateTime
import java.util.*
import javax.xml.bind.annotation.*

@XmlRootElement(name = "Barnevern")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
    name = "BarnevernType",
    propOrder = ["id", "datoUttrekk", "forrigeId", "fagsystem", "avgiver", "sak"]
)
data class BarnevernTypeJackson(
    @field:XmlAttribute(name = "Id", required = true)
    val id: UUID,

    @field:XmlAttribute(name = "ForrigeId")
    val forrigeId: String?,

    @field:XmlAttribute(name = "DatoUttrekk", required = true)
    @field:XmlSchemaType(name = "dateTime")
    val datoUttrekk: ZonedDateTime,

    @field:XmlElement(name = "Fagsystem", required = true)
    val fagsystem: FagsystemType,

    @field:XmlElement(name = "Avgiver", required = true)
    val avgiver: AvgiverType,

    @field:XmlElement(name = "Sak", type = SakType::class, required = true)
    val sak: SakTypeJackson
)
