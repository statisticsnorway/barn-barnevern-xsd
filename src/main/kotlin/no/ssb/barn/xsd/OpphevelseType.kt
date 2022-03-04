package no.ssb.barn.xsd

import no.ssb.barn.converter.LocalDateTimeAdapter
import java.time.ZonedDateTime
import javax.xml.bind.annotation.*
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OpphevelseType", propOrder = ["kode", "presisering", "sluttDato", "flytting"])
data class OpphevelseType(
    @field:XmlAttribute(name = "Kode", required = true)
    var kode: String = "1",

    @field:XmlAttribute(name = "Presisering")
    var presisering: String? = null,

    @field:XmlAttribute(name = "SluttDato", required = true)
    @field:XmlSchemaType(name = "dateTime")
    @field:XmlJavaTypeAdapter(
        LocalDateTimeAdapter::class
    )
    var sluttDato: ZonedDateTime = ZonedDateTime.now(),

    @field:XmlElement(name = "Flytting")
    var flytting: FlyttingType? = null
)
