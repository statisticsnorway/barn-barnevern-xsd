package no.ssb.barn.converter

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.xml.bind.annotation.adapters.XmlAdapter

class LocalDateAdapter : XmlAdapter<String, LocalDate>()  {

    override fun marshal(date: LocalDate?): String? =
        date?.format(DateTimeFormatter.ISO_LOCAL_DATE)

    override fun unmarshal(date: String): LocalDate =
        LocalDate.parse(date, DateTimeFormatter.ISO_LOCAL_DATE)
}