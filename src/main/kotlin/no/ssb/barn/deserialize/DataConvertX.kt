package no.ssb.barn.deserialize

import no.ssb.barn.xsd.BarnevernType
import javax.xml.XMLConstants
import javax.xml.bind.JAXBContext
import javax.xml.bind.Marshaller
import javax.xml.transform.Source
import javax.xml.validation.Schema
import javax.xml.validation.SchemaFactory

class DataConvertX {
    fun marshallXML(xsdFile: Source) {
        // create a SchemaFactory capable
        val factory: SchemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI)

        // load a schema, represented by a Schema instance
        val schema: Schema = factory.newSchema(xsdFile)

        //create JAXB context
        val context: JAXBContext = JAXBContext.newInstance(BarnevernType::class.java)
        val marshaller = context.createMarshaller()
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true)
        marshaller.marshal(schema, System.out).toString()
//        marshaller.marshal(subject,
//                FileOutputStream("./Barnevern.xml"))
    }

}