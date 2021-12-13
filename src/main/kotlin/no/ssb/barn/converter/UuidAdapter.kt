package no.ssb.barn.converter

import javax.xml.bind.annotation.adapters.XmlAdapter
import java.util.*

class UuidAdapter: XmlAdapter<String, UUID>() {

    override fun marshal(uuid: UUID): String = uuid.toString()

    override fun unmarshal(string: String): UUID = UUID.fromString(string)
}