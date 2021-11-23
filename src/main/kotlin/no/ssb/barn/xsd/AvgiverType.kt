package no.ssb.barn.xsd

import jakarta.xml.bind.annotation.XmlAccessType
import jakarta.xml.bind.annotation.XmlAccessorType
import jakarta.xml.bind.annotation.XmlAttribute
import jakarta.xml.bind.annotation.XmlType

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
    name = "AvgiverType",
    propOrder = ["organisasjonsnummer", "kommunenummer", "kommunenavn"]//,
//    factoryMethod = "createAvgiverType"
)
data class AvgiverType(
    @field:XmlAttribute(name = "Organisasjonsnummer", required = true)
    var organisasjonsnummer: String = "",

    @field:XmlAttribute(name = "Kommunenummer", required = true)
    var kommunenummer: String = "",

    @field:XmlAttribute(name = "Kommunenavn", required = true)
    var kommunenavn: String = ""
)/* {
    companion object {
        @JvmStatic
        fun createAvgiverType(): AvgiverType {
            return AvgiverType("", "", "")
        }
    }
}*/
