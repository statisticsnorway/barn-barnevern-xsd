package no.ssb.barn.xsd.jackson

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty
import no.ssb.barn.xsd.MeldingType
import java.time.LocalDate
import java.util.*
import javax.xml.bind.annotation.*

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
    name = "SakType",
    propOrder = ["id", "migrertId", "startDato", "sluttDato",
        "journalnummer", "fodselsnummer", "duFnummer", "fodseldato", "kjonn",
        "avsluttet", "melding", "undersokelse", "plan", "tiltak", "vedtak", "ettervern", "oversendelseBarneverntjeneste",
        "flytting", "relasjon", "slettet"]
)
data class SakTypeJackson(
    @field:XmlAttribute(name = "Id", required = true)
    val id: UUID,

    @field:XmlAttribute(name = "MigrertId")
    val migrertId: String?,

    @field:XmlAttribute(name = "StartDato", required = true)
    @field:XmlSchemaType(name = "date")
    val startDato: LocalDate,

    @field:XmlAttribute(name = "SluttDato")
    @field:XmlSchemaType(name = "date")
    val sluttDato: LocalDate?,

    @field:XmlAttribute(name = "Journalnummer", required = true)
    val journalnummer: String,

    @field:XmlAttribute(name = "Fodselsnummer")
    val fodselsnummer: String?,

    @field:XmlAttribute(name = "DUFnummer")
    val duFnummer: String?,

    @field:XmlAttribute(name = "Fodseldato")
    @field:XmlSchemaType(name = "date")
    val fodseldato: LocalDate?,

    @field:XmlAttribute(name = "Kjonn")
    val kjonn: String?,

    @field:XmlAttribute(name = "Avsluttet")
    val avsluttet: Boolean?
) {
    @JacksonXmlProperty(localName = "Melding")
    @JacksonXmlElementWrapper(useWrapping = false)
    var meldinger: List<MeldingType> = mutableListOf()
        set(value) {
            field = meldinger + value
        }
}
