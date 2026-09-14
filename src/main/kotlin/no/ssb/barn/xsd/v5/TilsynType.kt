package no.ssb.barn.xsd.v5

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper
import jakarta.xml.bind.annotation.*
import java.time.LocalDate

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
    name = "TilsynType", propOrder = ["ansvarlig", "hyppighet", "utfortDato"
    ]
)
data class TilsynType(
    @field:XmlElement(name = "Ansvarlig", required = true)
    @field:JacksonXmlElementWrapper(useWrapping = false)
    val ansvarlig: MutableList<TilsynAnsvarligType>,

    @field:XmlElement(name = "Hyppighet", required = true)
    @field:JacksonXmlElementWrapper(useWrapping = false)
    val hyppighet: MutableList<TilsynHyppighetType>,

    @field:XmlElement(name = "UtfortDato")
    @field:JacksonXmlElementWrapper(useWrapping = false)
    @XmlSchemaType(name = "date")
    val utfortDato: MutableList<LocalDate> = mutableListOf()
)