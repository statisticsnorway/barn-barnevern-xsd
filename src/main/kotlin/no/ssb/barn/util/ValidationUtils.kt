package no.ssb.barn.util

import javax.xml.XMLConstants
import javax.xml.validation.SchemaFactory
import javax.xml.validation.Validator


object ValidationUtils {

    @JvmStatic
    val VERSION_ONE_XSD = "Barnevern.xsd"

    /**
     * Returns a [Validator] that validates XML against the [VERSION_ONE_XSD] schema.
     *
     * @return a [Validator] that validates against the [VERSION_ONE_XSD] schema
     */
    @JvmStatic
    fun getSchemaValidator(): Validator = getSchemaValidator(VERSION_ONE_XSD)

    /**
     * Returns a [Validator] that validates XML against the given XSD resource.
     *
     * @param xsdResourceName the name of the XSD resource to validate against
     * @return a [Validator] that validates against the given XSD resource
     */
    @JvmStatic
    fun getSchemaValidator(xsdResourceName: String): Validator = SchemaFactory
        .newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI)
        .apply { setFeature("http://apache.org/xml/features/disallow-doctype-decl", true) }
        .newSchema(javaClass.classLoader.getResource(xsdResourceName))
        .newValidator()
}
