package no.ssb.barn.deserialize

import com.fasterxml.jackson.databind.JsonDeserializer
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import no.ssb.barn.xsd.BarnevernType
import java.io.IOException

class DataDeserializer {
    private val XML_MAPPER: XmlMapper = XmlMapper()
    private val JSON_MAPPER = ObjectMapper()

    fun getJsonMapper(): ObjectMapper {
        return JSON_MAPPER
    }

    fun getXmlMapper(): XmlMapper {
        return XML_MAPPER
    }

    fun xmlToJson(xml: String?): String? {
        return try {
            getJsonMapper().writeValueAsString(getXmlMapper().readTree(xml))
        } catch (e: IOException) {
            e.printStackTrace()
            ""
        }
    }
}
