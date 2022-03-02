package no.ssb.barn.converter

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.xml.bind.annotation.adapters.XmlAdapter

class LocalDateTimeAdapter : XmlAdapter<String, LocalDateTime>() {

    override fun marshal(dateTime: LocalDateTime?): String? =
        dateTime?.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)

    override fun unmarshal(dateTime: String?): LocalDateTime? =
        if (dateTime.isNullOrEmpty()) null
        else LocalDateTime.parse(dateTime, DateTimeFormatter.ISO_LOCAL_DATE_TIME)
}
