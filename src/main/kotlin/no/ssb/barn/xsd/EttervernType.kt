package no.ssb.barn.xsd

import jakarta.xml.bind.annotation.*
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter
import no.ssb.barn.converter.LocalDateAdapter
import no.ssb.barn.generator.RandomGenerator
import java.time.LocalDate

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "EttervernType", propOrder = ["id", "tilbudSendtDato", "konklusjon"])
data class EttervernType(
        @field:XmlAttribute(name = "Id", required = true)
        var id: String = RandomGenerator.generateRandomString(10),

        @field:XmlAttribute(name = "TilbudSendtDato", required = true)
        @field:XmlSchemaType(name = "date")
        @field:XmlJavaTypeAdapter(
                LocalDateAdapter::class)
        var tilbudSendtDato: LocalDate? = LocalDate.now(),

        @field:XmlElement(name = "Konklusjon")
        var konklusjon: EttervernKonklusjonType? = EttervernKonklusjonType()
)
