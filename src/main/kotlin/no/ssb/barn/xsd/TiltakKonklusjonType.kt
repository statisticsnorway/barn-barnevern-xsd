package no.ssb.barn.xsd

import no.ssb.barn.converter.LocalDateTimeAdapter
import java.time.LocalDateTime
import javax.xml.bind.annotation.*
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter

// TODO: DELETE
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TiltakKonklusjon", propOrder = ["sluttDato"])
data class TiltakKonklusjonType(
    @field:XmlAttribute(name = "SluttDato", required = true)
    @field:XmlSchemaType(name = "date")
    @field:XmlJavaTypeAdapter(
        LocalDateTimeAdapter::class
    )
    var sluttDato: LocalDateTime = LocalDateTime.now()
)
