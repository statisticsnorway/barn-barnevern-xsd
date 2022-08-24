package no.ssb.barn.xsd

import java.time.LocalDate
import java.util.*
import javax.xml.bind.annotation.*

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
    name = "SakType",
    propOrder = ["id", "startDato",
        "fodselsnummer", "fodseldato", "kjonn", "bostedskommunenummer", "duFnummer"
    ]
)data class PersonaliaType (
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

    @field:XmlAttribute(name = "Bostedskommunenummer")
    var bostedskommunenummer: String? = null,

    @field:XmlAttribute(name = "DUFnummer")
    var duFnummer: String? = null
)