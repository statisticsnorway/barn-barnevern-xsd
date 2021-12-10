package no.ssb.barn.xsd

import no.ssb.barn.converter.LocalDateAdapter
import java.time.LocalDate
import jakarta.xml.bind.annotation.*
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter
import no.ssb.barn.converter.UuidAdapter
import java.util.*

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OversendelsePrivatKravType", propOrder = ["id", "startDato", "konklusjon"])
data class OversendelsePrivatKravType(
    @field:XmlAttribute(name = "Id", required = true)
    @field:XmlJavaTypeAdapter(
        UuidAdapter::class
    )
    var id: UUID = UUID.randomUUID(),

    @field:XmlAttribute(name = "StartDato", required = true)
    @field:XmlSchemaType(name = "date")
    @field:XmlJavaTypeAdapter(
        LocalDateAdapter::class
    )
    var startDato: LocalDate = LocalDate.now(),

    @field:XmlElement(name = "Konklusjon")
    var konklusjon: OversendelsePrivatKravKonklusjonType? = null
)
