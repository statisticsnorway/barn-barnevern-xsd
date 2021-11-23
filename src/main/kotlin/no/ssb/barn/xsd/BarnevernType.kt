package no.ssb.barn.xsd

import no.ssb.barn.converter.LocalDateTimeAdapter
import java.time.LocalDateTime
import jakarta.xml.bind.annotation.*
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter

@XmlRootElement(name = "Barnevern")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
    name = "BarnevernType",
    propOrder = ["datoUttrekk", "fagsystem", "avgiver", "sak"]
)
data class BarnevernType(
    @field:XmlAttribute(name = "DatoUttrekk", required = true)
    @field:XmlSchemaType(name = "dateTime")
    @field:XmlJavaTypeAdapter(
        LocalDateTimeAdapter::class
    )
    var datoUttrekk: LocalDateTime,

    @field:XmlElement(name = "Fagsystem", required = true)
    var fagsystem: FagsystemType,

    @field:XmlElement(name = "Avgiver", required = true)
    var avgiver: AvgiverType,

    @field:XmlElement(name = "Sak", type = SakType::class, required = true)
    var sak: SakType
) {
    constructor() : this(datoUttrekk = LocalDateTime.now(), fagsystem = FagsystemType(), avgiver = AvgiverType(), sak = SakType.createSakType())
}