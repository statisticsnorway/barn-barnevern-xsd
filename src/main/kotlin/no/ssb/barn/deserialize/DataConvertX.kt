package no.ssb.barn.deserialize

import jakarta.xml.bind.JAXBContext
import jakarta.xml.bind.Unmarshaller
import no.ssb.barn.xsd.BarnevernType
import javax.xml.XMLConstants
import javax.xml.transform.Source
import javax.xml.validation.Schema
import javax.xml.validation.SchemaFactory


class DataConvertX {

    companion object {

        @JvmStatic
        fun unmarshallXML(xmlFile: Source): BarnevernType {
            val context: JAXBContext = JAXBContext.newInstance(BarnevernType::class.java)
            val unmarshalled = context.createUnmarshaller()

            return unmarshalled.unmarshal(xmlFile) as BarnevernType
        }

        @JvmStatic
        fun unmarshallXmlWithSchema(
            schemaSource: Source,
            xmlSource: Source
        ): BarnevernType? {
            val schemaFactory =
                SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI)
            val schema: Schema = schemaFactory.newSchema(schemaSource)

            val context: JAXBContext =
                JAXBContext.newInstance(BarnevernType::class.java)

            val unmarshaller: Unmarshaller = context.createUnmarshaller()
            unmarshaller.schema = schema

            return unmarshaller.unmarshal(xmlSource) as BarnevernType?
        }
    }
}