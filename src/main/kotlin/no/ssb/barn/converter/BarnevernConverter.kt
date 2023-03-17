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
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import no.ssb.barn.xsd.BarnevernType

object BarnevernConverter {

    @JvmStatic
    val XML_MAPPER = XmlMapper(JacksonXmlModule()).apply {
        this.setSerializationInclusion(JsonInclude.Include.NON_EMPTY)
            .registerKotlinModule()
            .registerModules(
                KotlinModule.Builder()
                    .enable(KotlinFeature.NullIsSameAsDefault)
                    .build()
            )
            .registerModule(JavaTimeModule())
            .registerModule(JaxbAnnotationModule())
            /** required to parse dates as LocalDate, else parsing error */
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
            .enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT)
            .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
    }

    @JvmStatic
    val OBJECT_MAPPER: ObjectMapper = ObjectMapper()
        .registerKotlinModule()
        .registerModules(
            KotlinModule.Builder()
                .enable(KotlinFeature.NullIsSameAsDefault)
                .build()
        )
        .setSerializationInclusion(JsonInclude.Include.NON_EMPTY)
        .registerModule(JavaTimeModule())
        .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS) // to parse the dates as LocalDate, else parsing error

    @JvmStatic
    fun unmarshallXml(xml: String): BarnevernType = unmarshallXml<BarnevernType>(xml)

    inline fun <reified T : Any> unmarshallXml(xml: String): T = XML_MAPPER.readValue(xml)

    @JvmStatic
    fun marshallInstance(instanceToSerialize: Any): String = XML_MAPPER.writeValueAsString(instanceToSerialize)
}
