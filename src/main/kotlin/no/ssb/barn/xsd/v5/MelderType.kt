package no.ssb.barn.xsd.v5

import jakarta.xml.bind.annotation.XmlAccessType
import jakarta.xml.bind.annotation.XmlAccessorType
import jakarta.xml.bind.annotation.XmlElement
import jakarta.xml.bind.annotation.XmlType

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
    name = "MelderType", propOrder = ["privatMelder", "offentligMelder"
    ]
)
data class MelderType(
    @field:XmlElement(name = "PrivatMelder")
    val privatMelder: String? = null,

    @field:XmlElement(name = "OffentligMelder")
    val offentligMelder: String? = null
)