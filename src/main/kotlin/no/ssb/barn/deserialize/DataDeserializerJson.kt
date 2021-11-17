package no.ssb.barn.deserialize

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonDeserializer
import com.fasterxml.jackson.databind.JsonNode
import java.io.IOException

class DataDeserializerJson : JsonDeserializer<BarnevernDTO?>() {
    @Throws(IOException::class)
    override fun deserialize(jsonParser: JsonParser, deserializationContext: DeserializationContext?): BarnevernDTO {
        val oc = jsonParser.codec
        val node: JsonNode = oc.readTree(jsonParser)

        return BarnevernDTO( node.get("userName").textValue(), node.get("userId").textValue(), null, null, null)
    }
}
