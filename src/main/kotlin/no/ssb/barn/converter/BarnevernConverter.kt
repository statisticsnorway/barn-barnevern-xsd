package no.ssb.barn.converter

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule
import com.fasterxml.jackson.dataformat.xml.XmlMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.jakarta.xmlbind.JakartaXmlBindAnnotationModule
import com.fasterxml.jackson.module.kotlin.KotlinFeature
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fasterxml.jackson.module.kotlin.readValue
import no.ssb.barn.xsd.BarnevernType

object BarnevernConverter {

    private val kotlinModule: KotlinModule = KotlinModule.Builder()
        .configure(KotlinFeature.StrictNullChecks, false)
        /**
         * required, else it will break for null
         * https://github.com/FasterXML/jackson-module-kotlin/issues/130#issuecomment-546625625
         */
        .configure(KotlinFeature.NullIsSameAsDefault, true)
        .build()

    @JvmStatic
    val XML_MAPPER = XmlMapper(JacksonXmlModule()).apply {
        this.setSerializationInclusion(JsonInclude.Include.NON_EMPTY)
            .registerModule(kotlinModule)
            .registerModule(JavaTimeModule())
            .registerModule(JakartaXmlBindAnnotationModule())
            /** required to parse dates as LocalDate, else parsing error */
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
            .enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT)
            .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
    }

    @JvmStatic
    val OBJECT_MAPPER: ObjectMapper = ObjectMapper()
        .registerModule(kotlinModule)
        .setSerializationInclusion(JsonInclude.Include.NON_EMPTY)
        .registerModule(JavaTimeModule())
        .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS) // to parse the dates as LocalDate, else parsing error

    @JvmStatic
    fun unmarshallXml(xml: String): BarnevernType = unmarshallXml<BarnevernType>(xml)

    inline fun <reified T : Any> unmarshallXml(xml: String): T = XML_MAPPER.readValue(xml)

    @JvmStatic
    fun marshallInstance(instanceToSerialize: Any): String = XML_MAPPER.writeValueAsString(instanceToSerialize)
}
