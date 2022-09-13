package no.ssb.barn.xsd

import javax.xml.bind.annotation.XmlEnumValue

enum class BegrepsType(val begrep: String) {

    @field:XmlEnumValue("Ettervern")
    ETTERVERN("Ettervern"),

    @field:XmlEnumValue("Flytting")
    FLYTTING("Flytting"),

    @field:XmlEnumValue("Melding")
    MELDING("Melding"),

    @field:XmlEnumValue("OversendelseBarneverntjeneste")
    OVERSENDELSE_BARNEVERNTJENESTE("OversendelseBarneverntjeneste"),

    @field:XmlEnumValue("Personalia")
    PERSONALIA("Personalia"),

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

    private var value: String? = null

    open fun begrepsType(v: String) {
        value = v
    }

    open fun value(): String? {
        return value
    }

    open fun fromValue(v: String): BegrepsType? {
        for (c in values()) {
            if (c.value == v) {
                return c
            }
        }
        throw IllegalArgumentException(v)
    }
}
