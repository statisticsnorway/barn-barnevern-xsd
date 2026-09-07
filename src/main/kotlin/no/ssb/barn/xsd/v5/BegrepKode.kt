package no.ssb.barn.xsd.v5

import jakarta.xml.bind.annotation.XmlEnum
import jakarta.xml.bind.annotation.XmlEnumValue
import jakarta.xml.bind.annotation.XmlType

@XmlType(name = "BegrepKode")
@XmlEnum
enum class BegrepKode(private val value: String) {
    @XmlEnumValue("Personalia")
    PERSONALIA("Personalia"),

    @XmlEnumValue("Henvendelse")
    HENVENDELSE("Henvendelse"),

    @XmlEnumValue("Undersokelse")
    UNDERSOKELSE("Undersokelse"),

    @XmlEnumValue("Plan")
    PLAN("Plan"),

    @XmlEnumValue("Tiltak")
    TILTAK("Tiltak"),

    @XmlEnumValue("Vedtak")
    VEDTAK("Vedtak"),

    @XmlEnumValue("OversendelseFylkesnemnd")
    OVERSENDELSE_FYLKESNEMND("OversendelseFylkesnemnd"),

    @XmlEnumValue("Flytting")
    FLYTTING("Flytting"),

    @XmlEnumValue("Relasjon")
    RELASJON("Relasjon")
}