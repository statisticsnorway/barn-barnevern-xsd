package no.ssb.barn.converter

import com.google.gson.JsonElement
import com.google.gson.JsonPrimitive
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer
import java.lang.reflect.Type
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

class GsonLocalDateTimeAdapter : JsonSerializer<ZonedDateTime?> {
    override fun serialize(
        date: ZonedDateTime?,
        typeOfSrc: Type?,
        context: JsonSerializationContext?
    ): JsonElement =
        JsonPrimitive(date?.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME))
}
