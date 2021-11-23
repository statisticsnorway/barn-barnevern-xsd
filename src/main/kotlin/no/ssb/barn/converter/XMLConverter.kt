package no.ssb.barn.converter

import no.ssb.barn.xsd.BarnevernType
import java.io.StringReader
import java.io.StringWriter
import javax.xml.bind.JAXB

class XMLConverter {
    companion object {
        @JvmStatic
        fun xmlToBarnevernType(xmlString: String): BarnevernType {
            return JAXB.unmarshal(StringReader(xmlString), BarnevernType::class.java) as BarnevernType
        }

        @JvmStatic
        fun barneverTypeToXml(barnevernType: BarnevernType): String {
            val sw = StringWriter()
            JAXB.marshal(barnevernType, sw)

            return sw.toString()
        }
    }
}


