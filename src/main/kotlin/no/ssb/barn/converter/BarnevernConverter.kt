package no.ssb.barn.converter

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import no.ssb.barn.xsd.BarnevernType
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

    @JvmStatic
    fun unmarshallXmlToMap(xml: String): Map<String, Any> {
        val json = gson.toJson(unmarshallXml(xml))
        return gson.fromJson<Map<String, Any>>(json)
    }

    @JvmStatic
    fun marshallInstance(barnevernType: BarnevernType): String =
        StringWriter().use {
            jaxbContext
                .createMarshaller()
                .marshal(barnevernType, it)
            return it.toString()
        }

    private val gson: Gson = GsonBuilder()
        .registerTypeAdapter(LocalDate::class.java, GsonLocalDateAdapter())
        .registerTypeAdapter(LocalDateTime::class.java, GsonLocalDateTimeAdapter())
        .create()

    private inline fun <reified T> Gson.fromJson(json: String) =
        fromJson<T>(json, object: TypeToken<T>() {}.type)
}