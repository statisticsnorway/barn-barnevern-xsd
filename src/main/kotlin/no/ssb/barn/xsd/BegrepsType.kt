package no.ssb.barn.xsd

import javax.xml.bind.annotation.XmlEnumValue

enum class BegrepsType(val begrep: String) {
    @XmlEnumValue("Melding")
    MELDING("Melding"),

    @XmlEnumValue("Undersokelse")
    UNDERSOKELSE("Undersokelse"),

    @XmlEnumValue("Plan")
    PLAN("Plan"),

    @XmlEnumValue("Tiltak")
    TILTAK("Tiltak"),

    @XmlEnumValue("Vedtak")
    VEDTAK("Vedtak"),

    @XmlEnumValue("Ettervern")
    ETTERVERN("Ettervern"),

    @XmlEnumValue("OversendelseBarneverntjeneste")
    OVERSENDELSE_BARNEVERNTJENESTE("OversendelseBarneverntjeneste"),

    @XmlEnumValue("Flytting")
    FLYTTING("Flytting");

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
