package no.ssb.barn.converter

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.xml.bind.annotation.adapters.XmlAdapter


class LocalDateTimeAdapter : XmlAdapter<String, LocalDateTime>() {
    private val dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
    override fun marshal(dateTime: LocalDateTime): String {
        return dateTime.format(dateFormat)
    }

    override fun unmarshal(dateTime: String): LocalDateTime {
        return LocalDateTime.parse(dateTime, dateFormat)
    }
}
