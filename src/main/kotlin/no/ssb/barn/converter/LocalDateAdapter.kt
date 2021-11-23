package no.ssb.barn.converter

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import jakarta.xml.bind.annotation.adapters.XmlAdapter

class LocalDateAdapter : XmlAdapter<String, LocalDate>()  {
    private val format = DateTimeFormatter.ofPattern("yyyy-MM-dd")

    override fun marshal(date: LocalDate?): String? {
        if (date != null) {
            return date.format(format)
        }

        return null
    }

    override fun unmarshal(date: String): LocalDate {
        return LocalDate.parse(date, format)
    }
}