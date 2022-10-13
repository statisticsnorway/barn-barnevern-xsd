package no.ssb.barn.util

import javax.xml.XMLConstants
import javax.xml.transform.stream.StreamSource
import javax.xml.validation.SchemaFactory
import javax.xml.validation.Validator


object ValidationUtils {

    fun getSchemaValidator(): Validator = SchemaFactory
        .newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI)
        .apply { setFeature("http://apache.org/xml/features/disallow-doctype-decl", true) }
        .newSchema(StreamSource(xsdAsText.byteInputStream()))
        .newValidator()

    private val xsdAsText = this.javaClass.getResource("/Barnevern.xsd")!!.readText()
}
