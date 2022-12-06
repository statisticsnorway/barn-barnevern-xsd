package no.ssb.barn.util

import javax.xml.XMLConstants
import javax.xml.validation.SchemaFactory
import javax.xml.validation.Validator


object ValidationUtils {

    @JvmStatic
    val VERSION_ONE_XSD = "Barnevern.xsd"

    @JvmStatic
    fun getSchemaValidator(): Validator = getSchemaValidator(VERSION_ONE_XSD)

    @JvmStatic
    fun getSchemaValidator(xsdResourceName: String): Validator = SchemaFactory
        .newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI)
        .apply { setFeature("http://apache.org/xml/features/disallow-doctype-decl", true) }
        .newSchema(javaClass.classLoader.getResource(xsdResourceName))
        .newValidator()
}
