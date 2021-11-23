package no.ssb.barn.converter

import no.ssb.barn.xsd.BarnevernType
import java.io.StringReader
import java.io.StringWriter
import jakarta.xml.bind.JAXB

class XMLConverter {
    companion object {
        @JvmStatic
        fun xmlToBarnevernType(xmlString: String): BarnevernType =
             JAXB.unmarshal(StringReader(xmlString), BarnevernType::class.java)


        @JvmStatic
        fun barneverTypeToXml(barnevernType: BarnevernType): String {
            val sw = StringWriter()
            JAXB.marshal(barnevernType, sw)

            return sw.toString()
        }
    }
}


