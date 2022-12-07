package no.ssb.barn.xsd

import java.time.LocalDate
import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlAttribute
import javax.xml.bind.annotation.XmlSchemaType
import javax.xml.bind.annotation.XmlType

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OversendelsePrivatKravKonklusjonType", propOrder = ["kode","sluttDato"])
data class OversendelsePrivatKravKonklusjonType(

    @field:XmlAttribute(name = "Kode", required = true)
    val kode: String,

    @field:XmlAttribute(name = "SluttDato", required = true)
    @field:XmlSchemaType(name = "date")
    val sluttDato: LocalDate
)
