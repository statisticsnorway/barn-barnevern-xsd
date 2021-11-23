package no.ssb.barn.xsd

import no.ssb.barn.converter.LocalDateAdapter
import java.time.LocalDate
import jakarta.xml.bind.annotation.*
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OversendelseBarneverntjenesteType", propOrder = ["id", "startDato", "lovhjemmel", "jmfrLovhjemmel"])
data class OversendelseBarneverntjenesteType(
    @field:XmlAttribute(name = "Id", required = true)
    var id: String,

    @field:XmlAttribute(name = "StartDato", required = true)
    @field:XmlSchemaType(name = "date")
    @field:XmlJavaTypeAdapter(
        LocalDateAdapter::class
    ) var startDato: LocalDate,

    @field:XmlElement(name = "Lovhjemmel", required = true)
    var lovhjemmel: LovhjemmelType,

    @field:XmlElement(name = "JmfrLovhjemmel")
    var jmfrLovhjemmel: List<LovhjemmelType>? = null
)
