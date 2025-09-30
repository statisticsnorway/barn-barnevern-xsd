package no.ssb.barn.xsd

import jakarta.xml.bind.annotation.XmlAccessType
import jakarta.xml.bind.annotation.XmlAccessorType
import jakarta.xml.bind.annotation.XmlElement
import jakarta.xml.bind.annotation.XmlSchemaType
import jakarta.xml.bind.annotation.XmlType
import java.time.LocalDate

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
    name = "BarnetsMedvirkningType",
    propOrder = ["startDato", "barnetsMedvirkningType"]
)
data class BarnetsMedvirkningType(
    @field:XmlElement(name = "StartDato", required = true)
    @field:XmlSchemaType(name = "date")
    val startDato: LocalDate,

    @field:XmlElement(name = "MedvirkningType", required = true)
    @field:XmlSchemaType(name = "string")
    val barnetsMedvirkningType: String,
)
