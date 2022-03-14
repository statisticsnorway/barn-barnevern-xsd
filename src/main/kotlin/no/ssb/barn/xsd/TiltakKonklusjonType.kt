package no.ssb.barn.xsd

import no.ssb.barn.converter.LocalDateTimeAdapter
import java.time.ZonedDateTime
import javax.xml.bind.annotation.*
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TiltakKonklusjon", propOrder = ["sluttDato"])
data class TiltakKonklusjonType(
    @field:XmlAttribute(name = "SluttDato", required = true)
    @field:XmlSchemaType(name = "dateTime")
    @field:XmlJavaTypeAdapter(
        LocalDateTimeAdapter::class
    )
    var sluttDato: ZonedDateTime = ZonedDateTime.now()
)