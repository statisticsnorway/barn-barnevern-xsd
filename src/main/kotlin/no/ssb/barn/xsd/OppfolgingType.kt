package no.ssb.barn.xsd

import no.ssb.barn.converter.LocalDateAdapter
import java.time.LocalDate
import jakarta.xml.bind.annotation.*
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OppfolgingType", propOrder = ["id", "utfortDato"])
data class OppfolgingType(
    @field:XmlAttribute(name = "Id", required = true)
    var id: String,

    @field:XmlAttribute(name = "UtfortDato", required = true)
    @field:XmlSchemaType(name = "date")
    @field:XmlJavaTypeAdapter(
        LocalDateAdapter::class
    )
    var utfortDato: LocalDate
)
