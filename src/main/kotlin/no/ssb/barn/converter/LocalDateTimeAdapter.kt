package no.ssb.barn.converter

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.xml.bind.annotation.adapters.XmlAdapter

class LocalDateTimeAdapter : XmlAdapter<String, LocalDateTime>() {
    private val dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")

    override fun marshal(dateTime: LocalDateTime): String =
        dateTime.format(dateFormat)

    override fun unmarshal(dateTime: String): LocalDateTime =
        LocalDateTime.parse(dateTime, dateFormat)
}
