package no.ssb.barn.validation.rule

import no.ssb.barn.validation.AbstractRule
import no.ssb.barn.validation.ValidationContext
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
    val xsdResourceName: String,
) : AbstractRule(
    WarningLevel.FATAL,
    "Validéring av innhold mot filbeskrivelse",
    "N/A"
) {
    override fun validate(context: ValidationContext): List<ReportEntry>? {
        try {
            getSchemaValidator(xsdResourceName)
                .validate(StreamSource(StringReader(context.xml)))

            return null
        } catch (e: SAXParseException) {
            return listOf(
                createReportEntry(
                    errorText = "Innholdet er feil i forhold til filbeskrivelsen / XSD: "
                            + String.format(
                        "Line: %d Column: %d Message: %s",
                        e.lineNumber, e.columnNumber, e.message
                    )
                )
            )
        }
    }

    private fun getSchemaValidator(xsdResourceName: String): Validator {
        val newInstance = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI)
        newInstance.setFeature("http://apache.org/xml/features/disallow-doctype-decl",true)
        return newInstance
            .newSchema(StreamSource(getSourceFromClasspath(xsdResourceName)))
            .newValidator()
    }

    private fun getSourceFromClasspath(resourceName: String): InputStream? =
        this::class.java.classLoader.getResource(resourceName)!!.openStream()
}