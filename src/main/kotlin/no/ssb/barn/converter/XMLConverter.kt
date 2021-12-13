package no.ssb.barn.converter

import javax.xml.bind.JAXB
import no.ssb.barn.xsd.BarnevernType
import java.io.StringReader
import java.io.StringWriter

class XMLConverter {
    companion object {

        @JvmStatic
        fun xmlToBarnevernType(xmlString: String): BarnevernType =
             JAXB.unmarshal(StringReader(xmlString), BarnevernType::class.java)

        @JvmStatic
        fun barnevernTypeToXml(barnevernType: BarnevernType): String {
            val stringWriter = StringWriter()
            JAXB.marshal(barnevernType, stringWriter)

            return stringWriter.toString()
        }
    }
}


