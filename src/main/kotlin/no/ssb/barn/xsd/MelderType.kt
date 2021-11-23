package no.ssb.barn.xsd

import no.ssb.barn.codelists.CodelistItem
import no.ssb.barn.generator.RandomGenerator
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneId
import java.time.ZonedDateTime
import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlAttribute
import javax.xml.bind.annotation.XmlType

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MelderType",
    propOrder = ["kode", "presisering"],
    factoryMethod = "createMelderType")
data class MelderType(
    @field:XmlAttribute(name = "Kode", required = true)
    var kode: String,

    @field:XmlAttribute(name = "Presisering")
    var presisering: String?
) {
    companion object {
        @JvmStatic
        fun createMelderType(): MelderType {
            return MelderType(
                "1",
                null
            )
        }

        @JvmStatic
        fun getCodes(date: LocalDate): List<CodelistItem> {
            return listOf(
                CodelistItem(
                    "1",
                    "Barnet selv",
                    LocalDate.of(2013, 1, 1)
                ),
                CodelistItem(
                    "2",
                    "Mor/ far/ foresatte",
                    LocalDate.of(2013, 1, 1)
                ),
                CodelistItem(
                    "3",
                    "Familie for øvrig",
                    LocalDate.of(2013, 1, 1)
                ),
                CodelistItem(
                    "4",
                    "Andre privatpersoner",
                    LocalDate.of(2013, 1, 1)
                ),
                CodelistItem(
                    "5",
                    "Barnvernstjenesten",
                    LocalDate.of(2013, 1, 1)
                ),
                CodelistItem(
                    "6",
                    "NAV (kommune og stat)",
                    LocalDate.of(2013, 1, 1)
                ),
                CodelistItem(
                    "7",
                    "Barnevernsvakt",
                    LocalDate.of(2013, 1, 1)
                ),
                CodelistItem(
                    "8",
                    "Politi",
                    LocalDate.of(2013, 1, 1)
                ),
                CodelistItem(
                    "9",
                    "Barnehage",
                    LocalDate.of(2013, 1, 1)
                ),
                CodelistItem(
                    "10",
                    "Helsestasjon/skolehelsetjenesten",
                    LocalDate.of(2013, 1, 1)
                ),
                CodelistItem(
                    "11",
                    "Skole",
                    LocalDate.of(2013, 1, 1)
                ),
                CodelistItem(
                    "12",
                    "Pedagogisk-psykologisk tjeneste (PPT)",
                    LocalDate.of(2013, 1, 1)
                ),
                CodelistItem(
                    "13",
                    "Psykisk helsevern for barn og unge (kommune og stat)",
                    LocalDate.of(2013, 1, 1)
                ),
                CodelistItem(
                    "14",
                    "Psykisk helsevern for voksne (kommune og stat)",
                    LocalDate.of(2013, 1, 1)
                ),
                CodelistItem(
                    "15",
                    "Lege/ sykehus/ tannlege",
                    LocalDate.of(2013, 1, 1)
                ),
                CodelistItem(
                    "16",
                    "Familievernkontor",
                    LocalDate.of(2013, 1, 1)
                ),
                CodelistItem(
                    "17",
                    "Tjenester og instanser med ansvar for oppfølging av personers rusproblemer (kommune og stat)",
                    LocalDate.of(2013, 1, 1)
                ),
                CodelistItem(
                    "18",
                    "Krisesenter",
                    LocalDate.of(2013, 1, 1)
                ),
                CodelistItem(
                    "19",
                    "Asylmottak/ UDI/ innvandringsmyndighet",
                    LocalDate.of(2013, 1, 1)
                ),
                CodelistItem(
                    "20",
                    "Utekontakt/ fritidsklubb",
                    LocalDate.of(2013, 1, 1)
                ),
                CodelistItem(
                    "21",
                    "Frivillige organisasjoner/ idrettslag",
                    LocalDate.of(2013, 1, 1)
                ),
                CodelistItem(
                    "22",
                    "Andre offentlige instanser (krever presisering)",
                    LocalDate.of(2013, 1, 1)
                ),
                CodelistItem(
                    "23",
                    "Anonym",
                    LocalDate.of(2013, 1, 1)
                )
            ).filter {
                (date.isEqual(it.validFrom) || date.isAfter(it.validFrom))
                        && (date.isBefore(it.validTo) || date.isEqual(it.validTo))
            }
        }

        @JvmStatic
        fun getRandomCode(date: LocalDate): String {
            return getCodes(date).filter { item -> !item.description.contains("krever presisering") }.random().code
        }
    }
}
