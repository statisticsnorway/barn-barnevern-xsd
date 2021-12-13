package no.ssb.barn.converter

import jakarta.xml.bind.annotation.adapters.XmlAdapter
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class LocalDateTimeAdapter : XmlAdapter<String, LocalDateTime>() {
    private val dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")

    override fun marshal(dateTime: LocalDateTime): String =
        dateTime.format(dateFormat)

    override fun unmarshal(dateTime: String): LocalDateTime =
        LocalDateTime.parse(dateTime, dateFormat)
}
