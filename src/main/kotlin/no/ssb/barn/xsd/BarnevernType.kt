package no.ssb.barn.xsd

import no.ssb.barn.converter.LocalDateTimeAdapter
import no.ssb.barn.converter.UuidAdapter
import java.time.ZonedDateTime
import java.util.*
import javax.xml.bind.annotation.*
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter

@XmlRootElement(name = "Barnevern")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
    name = "BarnevernType",
    propOrder = ["id", "datoUttrekk", "forrigeId", "fagsystem", "avgiver", "sak"]
)
data class BarnevernType(
    @field:XmlAttribute(name = "Id", required = true)
    @field:XmlJavaTypeAdapter(
        UuidAdapter::class
    )
    var id: UUID? = null,

    @field:XmlAttribute(name = "ForrigeId")
    var forrigeId: String? = null,

    @field:XmlAttribute(name = "DatoUttrekk", required = true)
    @field:XmlSchemaType(name = "dateTime")
    @field:XmlJavaTypeAdapter(
        LocalDateTimeAdapter::class
    )
    var datoUttrekk: ZonedDateTime? = null,

    @field:XmlElement(name = "Fagsystem", required = true)
    var fagsystem: FagsystemType = FagsystemType(),

    @field:XmlElement(name = "Avgiver", required = true)
    var avgiver: AvgiverType = AvgiverType(),

    @field:XmlElement(name = "Sak", type = SakType::class, required = true)
    var sak: SakType = SakType()
)