package no.ssb.barn.xsd.v5

import jakarta.xml.bind.annotation.XmlEnum
import jakarta.xml.bind.annotation.XmlEnumValue
import jakarta.xml.bind.annotation.XmlType

@XmlType(name = "LovKode")
@XmlEnum
enum class LovKode(private val value: String) {
    BVL("BVL"),

    @XmlEnumValue("BVL2021")
    BVL_2021("BVL2021"),

    @XmlEnumValue("BL1981")
    BL_1981("BL1981");
}