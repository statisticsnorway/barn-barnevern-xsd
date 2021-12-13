package no.ssb.barn.xsd

import jakarta.xml.bind.annotation.*
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter
import no.ssb.barn.converter.LocalDateAdapter
import no.ssb.barn.converter.UuidAdapter
import no.ssb.barn.generator.RandomUtils
import java.time.LocalDate
import java.util.*

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
    name = "SakType",
    propOrder = ["id", "migrertId", "startDato", "sluttDato",
        "journalnummer", "fodselsnummer", "duFnummer", "avsluttet", "virksomhet"]
)
data class SakType(
    @field:XmlAttribute(name = "Id", required = true)
    @field:XmlJavaTypeAdapter(
        UuidAdapter::class
    )
    var id: UUID = UUID.randomUUID(),

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
    var journalnummer: String = RandomUtils.generateRandomString(20),

    @field:XmlAttribute(name = "Fodselsnummer")
    var fodselsnummer: String? = null,

    @field:XmlAttribute(name = "DUFnummer")
    var duFnummer: String? = null,

    @field:XmlAttribute(name = "Avsluttet")
    var avsluttet: Boolean? = null,

    @field:XmlElement(name = "Virksomhet", required = true)
    var virksomhet: MutableList<VirksomhetType> = MutableList(1) { VirksomhetType() }
)
