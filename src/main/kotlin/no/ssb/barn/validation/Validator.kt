package no.ssb.barn.validation

import org.xml.sax.SAXException
import javax.xml.XMLConstants
import javax.xml.transform.Source
import javax.xml.validation.Schema
import javax.xml.validation.SchemaFactory
import javax.xml.validation.Validator

class Validator {
    companion object {
        @JvmStatic
        @Throws(SAXException::class)
        fun validateFromSources(xsdFile: Source, xmlFile: Source): Boolean {
            // create a SchemaFactory capable of understanding WXS schemas
            val factory: SchemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI)

            // load a WXS schema, represented by a Schema instance
            val schema: Schema = factory.newSchema(xsdFile)

            // create a Validator instance, which can be used to validate an instance document
            val validator: Validator = schema.newValidator()

            // validate the DOM tree
            validator.validate(xmlFile)
            return true
        }
    }
}