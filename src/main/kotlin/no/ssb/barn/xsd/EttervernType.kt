package no.ssb.barn.xsd

import no.ssb.barn.converter.LocalDateTimeAdapter
import no.ssb.barn.converter.UuidAdapter
import java.time.ZonedDateTime
import java.util.*
import javax.xml.bind.annotation.*
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "EttervernType", propOrder = ["id", "tilbudSendtDato", "konklusjon"])
data class EttervernType(
        @field:XmlAttribute(name = "Id", required = true)
        @field:XmlJavaTypeAdapter(
                UuidAdapter::class
        )
        var id: UUID = UUID.randomUUID(),

        @field:XmlAttribute(name = "TilbudSendtDato", required = true)
        @field:XmlSchemaType(name = "date")
        @field:XmlJavaTypeAdapter(
                LocalDateTimeAdapter::class)
        var tilbudSendtDato: ZonedDateTime? = ZonedDateTime.now(),

        @field:XmlElement(name = "Konklusjon")
        var konklusjon: EttervernKonklusjonType? = EttervernKonklusjonType()
)
