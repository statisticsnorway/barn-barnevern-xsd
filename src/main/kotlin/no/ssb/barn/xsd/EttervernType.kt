package no.ssb.barn.xsd

import javax.xml.bind.annotation.*
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter
import no.ssb.barn.converter.LocalDateAdapter
import no.ssb.barn.converter.UuidAdapter
import java.time.LocalDate
import java.util.*

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
                LocalDateAdapter::class)
        var tilbudSendtDato: LocalDate? = LocalDate.now(),

        @field:XmlElement(name = "Konklusjon")
        var konklusjon: EttervernKonklusjonType? = EttervernKonklusjonType()
)
