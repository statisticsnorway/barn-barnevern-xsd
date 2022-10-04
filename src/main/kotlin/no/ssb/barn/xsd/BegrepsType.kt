package no.ssb.barn.xsd

import javax.xml.bind.annotation.XmlEnumValue

enum class BegrepsType(val begrep: String) {

    @field:XmlEnumValue("Ettervern")
    ETTERVERN("Ettervern"),

    @field:XmlEnumValue("Flytting")
    FLYTTING("Flytting"),

    @field:XmlEnumValue("Melding")
    MELDING("Melding"),

    @field:XmlEnumValue("OversendelseFylkesnemnd")
    OVERSENDELSE_FYLKESNEMD("OversendelseFylkesnemnd"),

    @field:XmlEnumValue("Plan")
    PLAN("Plan"),

    @field:XmlEnumValue("Relasjon")
    RELASJON("Relasjon"),

    @field:XmlEnumValue("Status")
    STATUS("Vedtak"),

    @field:XmlEnumValue("Tiltak")
    TILTAK("Tiltak"),

    @field:XmlEnumValue("Undersokelse")
    UNDERSOKELSE("Undersokelse"),

    @field:XmlEnumValue("Vedtak")
    VEDTAK("Vedtak"),

    @field:XmlEnumValue("Personalia")
    PERSONALIA("Personalia");
}
