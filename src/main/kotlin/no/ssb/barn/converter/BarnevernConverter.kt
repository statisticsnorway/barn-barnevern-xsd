package no.ssb.barn.converter

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule
import com.fasterxml.jackson.dataformat.xml.XmlMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationModule
import com.fasterxml.jackson.module.kotlin.KotlinFeature
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import no.ssb.barn.xsd.BarnevernType
import no.ssb.barn.xsd.TiltakTypeJson
import java.time.LocalDate
import java.time.ZonedDateTime


object BarnevernConverter {

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
    fun unmarshallXml(xml: String): BarnevernType =
        xmlMapper.readValue(xml, BarnevernType::class.java)

    private const val VALIDATION_REPORT_KEY = "validationReport"

    @JvmStatic
    fun unmarshallXmlAndValidationReportToMap(
        xml: String,
        validationReportJson: String
    ): Map<String, Any> =
        gson.fromJson<MutableMap<String, Any>>(unmarshallXmlToJson(xml))
            .also {
                it[VALIDATION_REPORT_KEY] =
                    gson.fromJson<Map<String, Any>>(validationReportJson)
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
        }.let { barnevernType ->
            gson.toJson(barnevernType)
        }

    @JvmStatic
    fun marshallInstance(barnevernType: BarnevernType): String =
        xmlMapper.writeValueAsString(barnevernType)

    @JvmStatic
    val gson: Gson = GsonBuilder()
        .registerTypeAdapter(LocalDate::class.java, GsonLocalDateAdapter())
        .registerTypeAdapter(
            ZonedDateTime::class.java,
            GsonLocalDateTimeAdapter()
        )
        .create()

    private inline fun <reified T> Gson.fromJson(json: String) =
        fromJson<T>(json, object : TypeToken<T>() {}.type)
}