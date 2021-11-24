package no.ssb.barn.xsd

import jakarta.xml.bind.annotation.*
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter
import no.ssb.barn.converter.LocalDateAdapter
import no.ssb.barn.generator.RandomGenerator
import java.time.LocalDate

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
    name = "SakType",
    propOrder = ["id", "migrertId", "startDato", "sluttDato",
        "journalnummer", "fodselsnummer", "duFnummer", "avsluttet", "virksomhet"]
)
data class SakType(
    @field:XmlAttribute(name = "Id", required = true)
    var id: String = RandomGenerator.generateRandomString(10),

    @field:XmlAttribute(name = "MigrertId")
    var migrertId: String? = null,

    @field:XmlAttribute(name = "StartDato", required = true)
    @field:XmlSchemaType(name = "date")
    @field:XmlJavaTypeAdapter(
        LocalDateAdapter::class
    )
    var startDato: LocalDate = LocalDate.now(),

    @field:XmlAttribute(name = "SluttDato")
    @field:XmlSchemaType(name = "date")
    @field:XmlJavaTypeAdapter(
        LocalDateAdapter::class
    )
    var sluttDato: LocalDate? = null,

    @field:XmlAttribute(name = "Journalnummer", required = true)
    var journalnummer: String = RandomGenerator.generateRandomString(20),

    @field:XmlAttribute(name = "Fodselsnummer")
    var fodselsnummer: String? = null,

    @field:XmlAttribute(name = "DUFnummer")
    var duFnummer: String? = null,

    @field:XmlAttribute(name = "Avsluttet")
    var avsluttet: Boolean? = null,

    @field:XmlElement(name = "Virksomhet", required = true)
    var virksomhet: MutableList<VirksomhetType> = MutableList(1) { VirksomhetType() }
)
