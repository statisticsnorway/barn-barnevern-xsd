package no.ssb.barn.converter

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule
import com.fasterxml.jackson.dataformat.xml.XmlMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationModule
import com.fasterxml.jackson.module.kotlin.KotlinFeature
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fasterxml.jackson.module.kotlin.readValue
import no.ssb.barn.xsd.BarnevernType
import no.ssb.barn.xsd.TiltakTypeJson

object BarnevernConverter {

    private val kotlinModule: KotlinModule = KotlinModule.Builder()
        .configure(KotlinFeature.StrictNullChecks, false)
        // needed, else it will break for null https://github.com/FasterXML/jackson-module-kotlin/issues/130#issuecomment-546625625
        .configure(KotlinFeature.NullIsSameAsDefault, true)
        .build()

    private val XML_MAPPER = XmlMapper(JacksonXmlModule())
        .setSerializationInclusion(JsonInclude.Include.NON_EMPTY)
        .registerModule(kotlinModule)
        .registerModule(JavaTimeModule())
        .registerModule(JaxbAnnotationModule())
        .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS) // to parse the dates as LocalDate, else parsing error
        .enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT)
        .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)

    @JvmStatic
    val OBJECT_MAPPER: ObjectMapper = ObjectMapper()
        .registerModule(kotlinModule)
        .setSerializationInclusion(JsonInclude.Include.NON_EMPTY)
        .registerModule(JavaTimeModule())
        .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS) // to parse the dates as LocalDate, else parsing error

    private const val VALIDATION_REPORT_KEY = "validationReport"

    @JvmStatic
    fun unmarshallXml(xml: String): BarnevernType =
        XML_MAPPER.readValue(xml, BarnevernType::class.java)

    @JvmStatic
    fun unmarshallXmlAndValidationReportToMap(
        xml: String,
        validationReportJson: String
    ): Map<String, Any> = OBJECT_MAPPER.readValue<MutableMap<String, Any>>(unmarshallXmlToJson(xml))
        .also {
            it[VALIDATION_REPORT_KEY] = OBJECT_MAPPER.readValue<Map<String, Any>>(validationReportJson)
        }

    @JvmStatic
    fun unmarshallXmlToJson(xml: String): String =
        unmarshallXml(xml).apply {
            sak.apply {
                tiltak.clear()
                tiltak.addAll(sak.tiltak
                    .map { TiltakTypeJson(it) }
                    .toMutableList())
            }
        }.let { barnevernType -> OBJECT_MAPPER.writeValueAsString(barnevernType) }

    @JvmStatic
    fun marshallInstance(barnevernType: BarnevernType): String =
        XML_MAPPER.writeValueAsString(barnevernType)
}