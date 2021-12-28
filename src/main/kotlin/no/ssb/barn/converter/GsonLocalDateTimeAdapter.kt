package no.ssb.barn.converter

import com.google.gson.JsonElement
import com.google.gson.JsonPrimitive
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer
import java.lang.reflect.Type
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class GsonLocalDateTimeAdapter :
    JsonSerializer<LocalDateTime?> {
    override fun serialize(
        date: LocalDateTime?,
        typeOfSrc: Type?,
        context: JsonSerializationContext?
    ): JsonElement {
        return JsonPrimitive(date?.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))
    }
}
