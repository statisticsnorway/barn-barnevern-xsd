package no.ssb.barn.xsd

import java.time.LocalDate
import jakarta.xml.bind.annotation.XmlAccessType
import jakarta.xml.bind.annotation.XmlAccessorType
import jakarta.xml.bind.annotation.XmlAttribute
import jakarta.xml.bind.annotation.XmlSchemaType
import jakarta.xml.bind.annotation.XmlType

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "EttervernKonklusjon", propOrder = ["sluttDato", "kode"])
data class EttervernKonklusjonType(

    @field:XmlAttribute(name = "SluttDato", required = true)
    @field:XmlSchemaType(name = "date")
    val sluttDato: LocalDate,

    @field:XmlAttribute(name = "Kode", required = true)
    val kode: String
)
