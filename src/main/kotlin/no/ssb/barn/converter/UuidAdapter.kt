package no.ssb.barn.converter

import jakarta.xml.bind.annotation.adapters.XmlAdapter
import java.util.*

class UuidAdapter: XmlAdapter<String, UUID>() {
    override fun marshal(uuid: UUID): String {
        return uuid.toString()
    }

    override fun unmarshal(string: String): UUID {
        return UUID.fromString(string)
    }
}