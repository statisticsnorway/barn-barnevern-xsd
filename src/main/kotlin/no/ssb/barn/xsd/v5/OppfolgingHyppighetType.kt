package no.ssb.barn.xsd.v5

import jakarta.xml.bind.annotation.*
import java.math.BigInteger
import java.time.LocalDate

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
    name = "OppfolgingHyppighetType", propOrder = ["id", "dato", "hyppighetTidsenhet", "hyppighetAntall"]
)
data class OppfolgingHyppighetType(
    @field:XmlElement(name = "Id", required = true)
    val id: String,

    @field:XmlElement(name = "Dato", required = true)
    @XmlSchemaType(name = "date")
    val dato: LocalDate,

    @field:XmlElement(name = "HyppighetTidsenhet", required = true)
    val hyppighetTidsenhet: String,

    @field:XmlElement(name = "HyppighetAntall", required = true)
    val hyppighetAntall: BigInteger
)