package no.ssb.barn.xsd

import no.ssb.barn.converter.LocalDateAdapter
import java.time.LocalDate
import javax.xml.bind.annotation.*
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter
import javax.xml.datatype.XMLGregorianCalendar

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "EttervernType", propOrder = ["id", "tilbudSendtDato", "konklusjon"])
data class EttervernType(
        @field:XmlAttribute(name = "Id", required = true)
        var id: String,

        @field:XmlAttribute(name = "TilbudSendtDato", required = true)
        @field:XmlSchemaType(name = "date")
        @field:XmlJavaTypeAdapter(
                LocalDateAdapter::class)
        var tilbudSendtDato: LocalDate? = null,

        @field:XmlElement(name = "Konklusjon")
        var konklusjon: EttervernKonklusjonType? = null
)
