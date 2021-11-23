package no.ssb.barn.xsd

import no.ssb.barn.converter.LocalDateTimeAdapter
import java.time.LocalDateTime
import javax.xml.bind.annotation.*
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter

@XmlRootElement(name = "Barnevern")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
    name = "BarnevernType",
    propOrder = ["datoUttrekk", "fagsystem", "avgiver", "sak"],
    factoryMethod = "createBarnevernType"
)
data class BarnevernType(
    @field:XmlAttribute(name = "DatoUttrekk", required = true)
    @field:XmlSchemaType(name = "dateTime")
    @field:XmlJavaTypeAdapter(
        LocalDateTimeAdapter::class
    )
    var datoUttrekk: LocalDateTime?,

    @field:XmlElement(name = "Fagsystem", required = true)
    var fagsystem: FagsystemType,

    @field:XmlElement(name = "Avgiver", required = true)
    var avgiver: AvgiverType,

    @field:XmlElement(name = "Sak", required = true)
    var sak: SakType
) {
    companion object {
        @JvmStatic
        fun createBarnevernType(): BarnevernType? {
            return BarnevernType(
                LocalDateTime.now(), FagsystemType(), AvgiverType(), SakType.createSakType()
            )
        }
    }
}