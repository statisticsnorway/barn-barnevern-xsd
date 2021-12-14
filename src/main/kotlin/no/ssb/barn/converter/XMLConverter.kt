package no.ssb.barn.converter

import no.ssb.barn.xsd.BarnevernType
import java.io.StringReader
import java.io.StringWriter
import javax.xml.bind.JAXB

class XMLConverter {
    companion object {

        // NOTE: Duplicate of BarnevernDeserializer

        @JvmStatic
        fun xmlToBarnevernType(xmlString: String): BarnevernType =
             JAXB.unmarshal(StringReader(xmlString), BarnevernType::class.java)

        @JvmStatic
        fun barnevernTypeToXml(barnevernType: BarnevernType): String =
            StringWriter().use {
                JAXB.marshal(barnevernType, it)
                return it.toString()
            }
    }
}


