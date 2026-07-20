package no.ssb.barn.xsd.v5

import jakarta.xml.bind.annotation.*
import java.time.LocalDate

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
    name = "PersonaliaType",
    propOrder = ["id", "startDato", "fodselsnummer", "ufodt", "ukjent", "fodselsdato", "kjonn", "dufNummer", "erSlettet"
    ]
)
data class PersonaliaType(
    @field:XmlElement(name = "Id", required = true)
    val id: String,

    @field:XmlElement(name = "StartDato", required = true)
    @XmlSchemaType(name = "date")
    var startDato: LocalDate,

    @field:XmlElement(name = "Fodselsnummer")
    var fodselsnummer: String? = null,

    @field:XmlElement(name = "Ufodt")
    var isUfodt: Boolean? = null,

    @field:XmlElement(name = "Ukjent")
    var isUkjent: Boolean? = null,

    @field:XmlElement(name = "Fodselsdato")
    @XmlSchemaType(name = "date")
    var fodselsdato: LocalDate? = null,

    @field:XmlElement(name = "Kjonn")
    var kjonn: String? = null,

    @field:XmlElement(name = "DUFNummer")
    var dUFNummer: String? = null,

    @field:XmlElement(name = "ErSlettet")
    var erSlettet: Boolean? = null
)