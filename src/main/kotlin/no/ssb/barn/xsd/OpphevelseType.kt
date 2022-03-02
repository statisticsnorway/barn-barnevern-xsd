package no.ssb.barn.xsd

import no.ssb.barn.converter.LocalDateTimeAdapter
import no.ssb.barn.generator.RandomUtils
import java.time.LocalDateTime
import javax.xml.bind.annotation.*
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OpphevelseType", propOrder = ["kode", "presisering", "sluttDato"])
data class OpphevelseType(
    @field:XmlAttribute(name = "Kode", required = true)
    var kode: String = RandomUtils.generateRandomString(10),

    @field:XmlAttribute(name = "Presisering")
    var presisering: String? = null,

    @field:XmlAttribute(name = "SluttDato", required = true)
    @field:XmlSchemaType(name = "dateTime")
    @field:XmlJavaTypeAdapter(
        LocalDateTimeAdapter::class
    )
    var sluttDato: LocalDateTime = LocalDateTime.now()
)
