package no.ssb.barn.xsd

import java.time.LocalDate
import java.util.*
import jakarta.xml.bind.annotation.XmlAccessType
import jakarta.xml.bind.annotation.XmlAccessorType
import jakarta.xml.bind.annotation.XmlAttribute
import jakarta.xml.bind.annotation.XmlSchemaType
import jakarta.xml.bind.annotation.XmlType

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
    name = "SakType",
    propOrder = ["id", "startDato",
        "fodselsnummer", "fodseldato", "kjonn", "duFnummer"
    ]
)
data class PersonaliaType(
    @field:XmlAttribute(name = "Id", required = true)
    var id: UUID,

    @field:XmlAttribute(name = "StartDato", required = true)
    @field:XmlSchemaType(name = "date")
    var startDato: LocalDate,

    @field:XmlAttribute(name = "Fodselsnummer", required = true)
    var fodselsnummer: String,

    @field:XmlAttribute(name = "Fodseldato", required = true)
    @field:XmlSchemaType(name = "date")
    var fodseldato: LocalDate,

    @field:XmlAttribute(name = "Kjonn", required = true)
    val kjonn: String,

    @field:XmlAttribute(name = "DUFnummer")
    var duFnummer: String? = null
)
