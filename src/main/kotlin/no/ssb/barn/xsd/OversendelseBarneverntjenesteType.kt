package no.ssb.barn.xsd

import no.ssb.barn.converter.LocalDateAdapter
import java.time.LocalDate
import jakarta.xml.bind.annotation.*
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter
import no.ssb.barn.generator.RandomGenerator

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OversendelseBarneverntjenesteType", propOrder = ["id", "startDato", "lovhjemmel", "jmfrLovhjemmel"])
data class OversendelseBarneverntjenesteType(
    @field:XmlAttribute(name = "Id", required = true)
    var id: String = RandomGenerator.generateRandomString(10),

    @field:XmlAttribute(name = "StartDato", required = true)
    @field:XmlSchemaType(name = "date")
    @field:XmlJavaTypeAdapter(
        LocalDateAdapter::class
    ) var startDato: LocalDate = LocalDate.now(),

    @field:XmlElement(name = "Lovhjemmel", required = true)
    var lovhjemmel: LovhjemmelType = LovhjemmelType(),

    @field:XmlElement(name = "JmfrLovhjemmel")
    var jmfrLovhjemmel: MutableList<LovhjemmelType>? = MutableList(1) { LovhjemmelType() }
)
