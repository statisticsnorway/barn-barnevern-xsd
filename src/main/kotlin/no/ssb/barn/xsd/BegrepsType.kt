package no.ssb.barn.xsd

import javax.xml.bind.annotation.XmlEnumValue

enum class BegrepsType(val begrep: String) {
    @field:XmlEnumValue("Melding")
    MELDING("Melding"),

    @field:XmlEnumValue("Undersokelse")
    UNDERSOKELSE("Undersokelse"),

    @field:XmlEnumValue("Plan")
    PLAN("Plan"),

    @field:XmlEnumValue("Tiltak")
    TILTAK("Tiltak"),

    @field:XmlEnumValue("Vedtak")
    VEDTAK("Vedtak"),

    @field:XmlEnumValue("Status")
    STATUS("Vedtak"),

    @field:XmlEnumValue("Ettervern")
    ETTERVERN("Ettervern"),

    @field:XmlEnumValue("Relasjon")
    RELASJON("Relasjon"),

    @field:XmlEnumValue("OversendelseBarneverntjeneste")
    OVERSENDELSE_BARNEVERNTJENESTE("OversendelseBarneverntjeneste");

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
