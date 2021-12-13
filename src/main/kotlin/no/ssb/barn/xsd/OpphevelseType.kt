package no.ssb.barn.xsd

import javax.xml.bind.annotation.*
import no.ssb.barn.generator.RandomUtils

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OpphevelseType", propOrder = ["kode", "presisering"])
data class OpphevelseType(
    @field:XmlAttribute(name = "Kode", required = true)
        var kode: String = RandomUtils.generateRandomString(10),

    @field:XmlAttribute(name = "Presisering")
        var presisering: String? = null
)
