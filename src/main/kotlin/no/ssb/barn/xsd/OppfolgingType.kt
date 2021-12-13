package no.ssb.barn.xsd

import no.ssb.barn.converter.LocalDateAdapter
import java.time.LocalDate
import javax.xml.bind.annotation.*
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter
import no.ssb.barn.converter.UuidAdapter
import java.util.*

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OppfolgingType", propOrder = ["id", "utfortDato"])
data class OppfolgingType(
    @field:XmlAttribute(name = "Id", required = true)
    @field:XmlJavaTypeAdapter(
        UuidAdapter::class
    )
    var id: UUID = UUID.randomUUID(),

    @field:XmlAttribute(name = "UtfortDato", required = true)
    @field:XmlSchemaType(name = "date")
    @field:XmlJavaTypeAdapter(
        LocalDateAdapter::class
    )
    var utfortDato: LocalDate = LocalDate.now()
)
