package no.ssb.barn.xsd.v5

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper
import jakarta.xml.bind.annotation.*
import java.time.LocalDate

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
    name = "OppfolgingType", propOrder = ["hyppighet", "utfortDato"]
)
data class OppfolgingType(
    @field:XmlElement(name = "Hyppighet", required = true)
    @field:JacksonXmlElementWrapper(useWrapping = false)
    val hyppighet: MutableList<OppfolgingHyppighetType>,

    @field:XmlElement(name = "UtfortDato")
    @field:JacksonXmlElementWrapper(useWrapping = false)
    @XmlSchemaType(name = "date")
    val utfortDato: MutableList<LocalDate> = mutableListOf()
)