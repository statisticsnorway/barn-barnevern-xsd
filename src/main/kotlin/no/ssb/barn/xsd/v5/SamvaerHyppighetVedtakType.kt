package no.ssb.barn.xsd.v5

import jakarta.xml.bind.annotation.*
import java.math.BigInteger
import java.time.LocalDate
import java.util.UUID

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
    name = "SamvaerHyppighetVedtakType", propOrder = ["id", "part", "hyppighetTidsenhet", "hyppighetAntall", "dato"]
)
data class SamvaerHyppighetVedtakType(
    @field:XmlElement(name = "Id", required = true)
    val id: UUID,

    @field:XmlElement(name = "Part", required = true)
    val part: String,

    @field:XmlElement(name = "HyppighetTidsenhet", required = true)
    val hyppighetTidsenhet: String,

    @field:XmlElement(name = "HyppighetAntall", required = true)
    val hyppighetAntall: Int,

    @field:XmlElement(name = "Dato", required = true)
    @XmlSchemaType(name = "date")
    val dato: LocalDate
)