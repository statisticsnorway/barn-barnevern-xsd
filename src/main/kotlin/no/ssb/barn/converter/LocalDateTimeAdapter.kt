package no.ssb.barn.converter

import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import javax.xml.bind.annotation.adapters.XmlAdapter

class LocalDateTimeAdapter : XmlAdapter<String, ZonedDateTime>() {

    override fun marshal(dateTime: ZonedDateTime?): String? {
        if (dateTime == null) {
            return null
        }
        return dateTime.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME)
    }

    override fun unmarshal(dateTime: String?): ZonedDateTime? =
        if (dateTime.isNullOrEmpty()) null
        else ZonedDateTime.parse(dateTime, DateTimeFormatter.ISO_ZONED_DATE_TIME)
}
