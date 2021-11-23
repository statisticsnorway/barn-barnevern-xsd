package no.ssb.barn.deserialize

import jakarta.xml.bind.JAXBContext
import jakarta.xml.bind.Unmarshaller
import no.ssb.barn.xsd.BarnevernType
import java.io.InputStream
import javax.xml.XMLConstants
import javax.xml.transform.stream.StreamSource
import javax.xml.validation.SchemaFactory


class BarnevernDeserializer {

    companion object {

        @JvmStatic
        fun unmarshallXml(xml: String): BarnevernType =
            unmarshallXml(xml.byteInputStream())

        @JvmStatic
        fun unmarshallXml(xmlStream: InputStream): BarnevernType =
            JAXBContext.newInstance(BarnevernType::class.java)
                .createUnmarshaller()
                .unmarshal(xmlStream) as BarnevernType

        @JvmStatic
        fun unmarshallXmlWithSchema(
            schemaStream: InputStream,
            xmlStream: InputStream
        ): BarnevernType? {
            val unmarshaller: Unmarshaller = JAXBContext
                .newInstance(BarnevernType::class.java)
                .createUnmarshaller()

            unmarshaller.schema =
                SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI)
                    .newSchema(StreamSource(schemaStream))

            return unmarshaller.unmarshal(xmlStream) as BarnevernType?
        }
    }
}