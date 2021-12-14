package no.ssb.barn.converter

import no.ssb.barn.xsd.BarnevernType
import java.io.StringWriter
import javax.xml.bind.JAXBContext

class BarnevernConverter {
    companion object {

        private val jaxbContext: JAXBContext =
            JAXBContext.newInstance(BarnevernType::class.java)

        @JvmStatic
        fun unmarshallXml(xml: String): BarnevernType =
            jaxbContext
                .createUnmarshaller()
                .unmarshal(xml.byteInputStream()) as BarnevernType

        @JvmStatic
        fun marshallXml(barnevernType: BarnevernType): String =
            StringWriter().use {
                jaxbContext
                    .createMarshaller()
                    .marshal(barnevernType, it)
                return it.toString()
            }
    }
}