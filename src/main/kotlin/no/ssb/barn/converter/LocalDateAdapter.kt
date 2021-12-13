package no.ssb.barn.converter

import javax.xml.bind.annotation.adapters.XmlAdapter
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class LocalDateAdapter : XmlAdapter<String, LocalDate>()  {
    private val format = DateTimeFormatter.ofPattern("yyyy-MM-dd")

    override fun marshal(date: LocalDate?): String? = date?.format(format)

    override fun unmarshal(date: String): LocalDate =
        LocalDate.parse(date, format)
}