package no.ssb.barn.xsd

import jakarta.xml.bind.annotation.*
import no.ssb.barn.codelists.CodeListItem
import java.time.LocalDate

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
    name = "MelderType",
    propOrder = ["kode", "presisering"]
)
data class MelderType(
    @field:XmlAttribute(name = "Kode", required = true)
    var kode: String = getCodes(LocalDate.now())[0].code,

    @field:XmlAttribute(name = "Presisering")
    var presisering: String? = ""
) {
    companion object {
        @JvmStatic
        fun getCodes(date: LocalDate): List<CodeListItem> {
            return listOf(
                CodeListItem(
                    "1",
                    "Barnet selv",
                    LocalDate.of(2013, 1, 1)
                ),
                CodeListItem(
                    "2",
                    "Mor/ far/ foresatte",
                    LocalDate.of(2013, 1, 1)
                ),
                CodeListItem(
                    "3",
                    "Familie for øvrig",
                    LocalDate.of(2013, 1, 1)
                ),
                CodeListItem(
                    "4",
                    "Andre privatpersoner",
                    LocalDate.of(2013, 1, 1)
                ),
                CodeListItem(
                    "5",
                    "Barnvernstjenesten",
                    LocalDate.of(2013, 1, 1)
                ),
                CodeListItem(
                    "6",
                    "NAV (kommune og stat)",
                    LocalDate.of(2013, 1, 1)
                ),
                CodeListItem(
                    "7",
                    "Barnevernsvakt",
                    LocalDate.of(2013, 1, 1)
                ),
                CodeListItem(
                    "8",
                    "Politi",
                    LocalDate.of(2013, 1, 1)
                ),
                CodeListItem(
                    "9",
                    "Barnehage",
                    LocalDate.of(2013, 1, 1)
                ),
                CodeListItem(
                    "10",
                    "Helsestasjon/skolehelsetjenesten",
                    LocalDate.of(2013, 1, 1)
                ),
                CodeListItem(
                    "11",
                    "Skole",
                    LocalDate.of(2013, 1, 1)
                ),
                CodeListItem(
                    "12",
                    "Pedagogisk-psykologisk tjeneste (PPT)",
                    LocalDate.of(2013, 1, 1)
                ),
                CodeListItem(
                    "13",
                    "Psykisk helsevern for barn og unge (kommune og stat)",
                    LocalDate.of(2013, 1, 1)
                ),
                CodeListItem(
                    "14",
                    "Psykisk helsevern for voksne (kommune og stat)",
                    LocalDate.of(2013, 1, 1)
                ),
                CodeListItem(
                    "15",
                    "Lege/ sykehus/ tannlege",
                    LocalDate.of(2013, 1, 1)
                ),
                CodeListItem(
                    "16",
                    "Familievernkontor",
                    LocalDate.of(2013, 1, 1)
                ),
                CodeListItem(
                    "17",
                    "Tjenester og instanser med ansvar for oppfølging av personers rusproblemer (kommune og stat)",
                    LocalDate.of(2013, 1, 1)
                ),
                CodeListItem(
                    "18",
                    "Krisesenter",
                    LocalDate.of(2013, 1, 1)
                ),
                CodeListItem(
                    "19",
                    "Asylmottak/ UDI/ innvandringsmyndighet",
                    LocalDate.of(2013, 1, 1)
                ),
                CodeListItem(
                    "20",
                    "Utekontakt/ fritidsklubb",
                    LocalDate.of(2013, 1, 1)
                ),
                CodeListItem(
                    "21",
                    "Frivillige organisasjoner/ idrettslag",
                    LocalDate.of(2013, 1, 1)
                ),
                CodeListItem(
                    "22",
                    "Andre offentlige instanser (krever presisering)",
                    LocalDate.of(2013, 1, 1)
                ),
                CodeListItem(
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
