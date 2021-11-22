package no.ssb.barn.deserialize

import no.ssb.barn.xsd.BarnevernType
import java.io.File
import java.io.FileInputStream
import java.io.IOException
import javax.xml.XMLConstants
import javax.xml.bind.JAXBContext
import javax.xml.bind.Marshaller
import javax.xml.bind.Unmarshaller
import javax.xml.transform.Source
import javax.xml.transform.stream.StreamSource
import javax.xml.validation.Schema
import javax.xml.validation.SchemaFactory
import kotlin.reflect.jvm.internal.impl.builtins.jvm.JvmBuiltIns.Settings


class DataConvertX {

    fun unmarshallXML(schemaFile: Source): BarnevernType? {
        val context: JAXBContext = JAXBContext.newInstance(BarnevernType::class.java)
        val unmarshalled = context.createUnmarshaller()

        return unmarshalled.unmarshal(schemaFile) as BarnevernType
    }

    fun unmarshallXMLwithSchema(schemaFile: File): BarnevernType? {
        val schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI)
        val schema: Schema = schemaFactory.newSchema(schemaFile)

        val classLoader: ClassLoader = BarnevernType::class.java.getClassLoader()
        val xsdStream = classLoader.getResourceAsStream(schemaFile.path)
        val xsdSource = StreamSource(xsdStream)

        val context: JAXBContext = JAXBContext.newInstance(BarnevernType::class.java)
        val unmarshaller: Unmarshaller = context.createUnmarshaller()
        unmarshaller.schema = schema

        return unmarshaller.unmarshal(xsdSource) as BarnevernType?
    }

}