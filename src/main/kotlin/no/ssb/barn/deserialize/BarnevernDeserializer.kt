package no.ssb.barn.deserialize

import jakarta.xml.bind.JAXBContext
import no.ssb.barn.xsd.BarnevernType
import java.io.StringWriter

class BarnevernDeserializer {
    companion object {
        @JvmStatic
        fun unmarshallXml(xml: String): BarnevernType =
            JAXBContext.newInstance(BarnevernType::class.java)
                .createUnmarshaller()
                .unmarshal(xml.byteInputStream()) as BarnevernType

        @JvmStatic
        fun marshallXml(barnevernType: BarnevernType): String =
            StringWriter().use {
                JAXBContext.newInstance(BarnevernType::class.java)
                    .createMarshaller()
                    .marshal(barnevernType, it)
                return it.toString()
            }
    }
}