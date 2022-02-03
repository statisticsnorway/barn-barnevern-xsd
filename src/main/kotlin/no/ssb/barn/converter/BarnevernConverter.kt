package no.ssb.barn.converter

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import no.ssb.barn.xsd.BarnevernType
import no.ssb.barn.xsd.TiltakTypeJson
import java.io.StringWriter
import java.time.LocalDate
import java.time.LocalDateTime
import javax.xml.bind.JAXBContext


object BarnevernConverter {

    private val jaxbContext: JAXBContext =
        JAXBContext.newInstance(BarnevernType::class.java)

    @JvmStatic
    fun unmarshallXml(xml: String): BarnevernType =
        jaxbContext
            .createUnmarshaller()
            .unmarshal(xml.byteInputStream()) as BarnevernType

    private const val VALIDATION_REPORT_KEY = "validationReport"

    @JvmStatic
    fun unmarshallXmlAndValidationReportToMap(
        xml: String,
        validationReportJson: String
    ): Map<String, Any> =
        unmarshallXml(xml).apply {
            sak.virksomhet = sak.virksomhet
                .map { virksomhet ->
                    virksomhet.apply {
                        tiltak = virksomhet.tiltak
                            .map { TiltakTypeJson(it) }
                            .toMutableList()
                    }
                }.toMutableList()
        }.let { barnevernType ->
            gson.fromJson<MutableMap<String, Any>>(gson.toJson(barnevernType))
                .also {
                    it[VALIDATION_REPORT_KEY] =
                        gson.fromJson<Map<String, Any>>(validationReportJson)
                }
        }

    @JvmStatic
    fun marshallInstance(barnevernType: BarnevernType): String =
        StringWriter().use {
            jaxbContext
                .createMarshaller()
                .marshal(barnevernType, it)
            return it.toString()
        }

    @JvmStatic
    val gson: Gson = GsonBuilder()
        .registerTypeAdapter(LocalDate::class.java, GsonLocalDateAdapter())
        .registerTypeAdapter(
            LocalDateTime::class.java,
            GsonLocalDateTimeAdapter()
        )
        .create()

    private inline fun <reified T> Gson.fromJson(json: String) =
        fromJson<T>(json, object : TypeToken<T>() {}.type)
}