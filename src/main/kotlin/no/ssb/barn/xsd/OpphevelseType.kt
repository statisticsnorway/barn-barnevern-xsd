package no.ssb.barn.xsd

import no.ssb.barn.converter.LocalDateTimeAdapter
import java.time.ZonedDateTime
import javax.xml.bind.annotation.*
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OpphevelseType", propOrder = ["kode", "presisering"])
data class OpphevelseType(
    @field:XmlAttribute(name = "Kode", required = true)
    var kode: String = "1",

    @field:XmlAttribute(name = "Presisering")
    var presisering: String? = null,
)
