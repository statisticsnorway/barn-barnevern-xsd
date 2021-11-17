package no.ssb.barn.validation2

import no.ssb.barn.framework.AbstractRule
import no.ssb.barn.framework.ValidationContext
import no.ssb.barn.report.ReportEntry
import no.ssb.barn.report.WarningLevel
import org.xml.sax.SAXParseException
import java.io.InputStream
import java.io.StringReader
import javax.xml.XMLConstants
import javax.xml.transform.stream.StreamSource
import javax.xml.validation.SchemaFactory
import javax.xml.validation.Validator

class XsdRule(
    val xsdResourceName: String
) : AbstractRule(WarningLevel.FATAL, "XSD validation") {

    override fun validate(context: ValidationContext): ReportEntry? {
        try {
            getSchemaValidator(xsdResourceName)
                .validate(StreamSource(StringReader(context.xml)))
        } catch (e: SAXParseException) {
            return createReportEntry(
                String.format(
                    "Line: %d Column: %d Message: %s",
                    e.lineNumber, e.columnNumber, e.message
                )
            )
        }
        return null
    }

    private fun getSchemaValidator(xsdResourceName: String): Validator {
        return SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI)
            .newSchema(StreamSource(getSourceFromClasspath(xsdResourceName)))
            .newValidator()
    }

    private fun getSourceFromClasspath(resourceName: String): InputStream? {
        return this::class.java.classLoader.getResource(resourceName)
            ?.openStream()
    }
}