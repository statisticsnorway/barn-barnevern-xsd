package no.ssb.barn.xsd

import no.ssb.barn.converter.LocalDateAdapter
import no.ssb.barn.generator.RandomGenerator
import java.time.LocalDate
import jakarta.xml.bind.annotation.*
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
    name = "SakType",
    propOrder = ["id", "migrertId", "startDato", "sluttDato",
        "journalnummer", "fodselsnummer", "duFnummer", "avsluttet", "virksomhet"],
    factoryMethod = "createSakType"
)
data class SakType(
    @field:XmlAttribute(name = "Id", required = true)
    var id: String,

    @field:XmlAttribute(name = "MigrertId")
    var migrertId: String? = null,

    @field:XmlAttribute(name = "StartDato", required = true)
    @field:XmlSchemaType(name = "date")
    @field:XmlJavaTypeAdapter(
        LocalDateAdapter::class
    )
    var startDato: LocalDate,

    @field:XmlAttribute(name = "SluttDato")
    @field:XmlSchemaType(name = "date")
    @field:XmlJavaTypeAdapter(
        LocalDateAdapter::class
    )
    var sluttDato: LocalDate? = null,

    @field:XmlAttribute(name = "Journalnummer", required = true)
    var journalnummer: String,

    @field:XmlAttribute(name = "Fodselsnummer")
    var fodselsnummer: String? = null,

    @field:XmlAttribute(name = "DUFnummer")
    var duFnummer: String? = null,

    @field:XmlAttribute(name = "Avsluttet")
    var avsluttet: Boolean? = null,

    @field:XmlElement(name = "Virksomhet", required = true)
    var virksomhet: MutableList<VirksomhetType?>
) {
    companion object {
        @JvmStatic
        fun createSakType(): SakType {
            return SakType(
                RandomGenerator.generateRandomString(10),
                null,
                LocalDate.now(),
                null,
                RandomGenerator.generateRandomString(20),
                "02011088123",
                "",
                null,
                MutableList(1) { VirksomhetType.createVirksomhetType() }
            )
        }
    }
}
