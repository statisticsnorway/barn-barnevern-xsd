package no.ssb.barn.converter

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule
import com.fasterxml.jackson.dataformat.xml.XmlMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationModule
import com.fasterxml.jackson.module.kotlin.KotlinFeature
import com.fasterxml.jackson.module.kotlin.KotlinModule
import no.ssb.barn.xsd.jackson.BarnevernTypeJackson

object BarnvernConverterJackson {

    private val kotlinModule: KotlinModule = KotlinModule.Builder()
        .configure(KotlinFeature.StrictNullChecks, false)
        // needed, else it will break for null https://github.com/FasterXML/jackson-module-kotlin/issues/130#issuecomment-546625625
        .configure(KotlinFeature.NullIsSameAsDefault, true)
        .build()

    private val xmlMapper =
        XmlMapper(JacksonXmlModule())
            .registerModule(kotlinModule)
            .registerModule(JavaTimeModule())
            .registerModule(JaxbAnnotationModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS) // to parse the dates as LocalDate, else parsing error
            .enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT)
            .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)

    @JvmStatic
    fun unmarshallXml(xml: String): BarnevernTypeJackson {
        return xmlMapper.readValue(xml, BarnevernTypeJackson::class.java)
    }

    @JvmStatic
    fun marshallInstance(barnevernType: BarnevernTypeJackson): String {
        return xmlMapper.writeValueAsString(barnevernType)
    }
}