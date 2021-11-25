package no.ssb.barn.deserialize

import jakarta.xml.bind.JAXBContext
import no.ssb.barn.xsd.BarnevernType

class BarnevernDeserializer {
    companion object {
        @JvmStatic
        fun unmarshallXml(xml: String): BarnevernType =
            JAXBContext.newInstance(BarnevernType::class.java)
                .createUnmarshaller()
                .unmarshal(xml.byteInputStream()) as BarnevernType
    }
}