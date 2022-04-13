package no.ssb.barn.xsd

import no.ssb.barn.util.TypeUtils
import java.time.LocalDate
import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlAttribute
import javax.xml.bind.annotation.XmlType

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
    name = "ArsakFraType",
    propOrder = ["kode", "presisering"]
)
data class ArsakFraType(
    @field:XmlAttribute(name = "Kode", required = true)
    val kode: String,

    @field:XmlAttribute(name = "Presisering")
    val presisering: String? = null
) {
    companion object {

        @JvmStatic
        fun getCodes(date: LocalDate): List<CodeListItem> =
            TypeUtils.getCodes(date, codeList)

        private val validFrom = LocalDate.of(2022, 1, 1)

        private val codeList = listOf(
            CodeListItem("1.1.1", "Omsorgsplasseringen opphører ", validFrom),
            CodeListItem("1.1.2", "Barnet blir myndig ", validFrom),
            CodeListItem(
                "1.1.3",
                "Fosterforeldre klarer ikke å dekke barnets behov (forhold ved fosterhjemmet) ",
                validFrom
            ),
            CodeListItem(
                "1.1.4",
                "Barnet har behov for annet plasseringssted (institusjon, TFCO-fosterhjem, forsterket fosterhjem, osv. ",
                validFrom
            ),
            CodeListItem(
                "1.1.5",
                "Andre grunner; (f.eks. uenighet om oppdragets omfang, økonomi, forsterkningstiltak mv.) (krever presisering)",
                validFrom
            ),
            CodeListItem(
                "1.2.1",
                "Barnet har behov fosterforeldre ikke kan dekke ",
                validFrom
            ),
            CodeListItem(
                "1.2.2",
                "Endring i fosterforeldres livssituasjon (skilsmisse, død, osv.) ",
                validFrom
            ),
            CodeListItem(
                "1.2.3",
                "Andre grunner; (f.eks. uenighet om oppdragets omfang, økonomi, forsterkningstiltak, manglende eller lite effektiv veiledning, mv.) (krever presisering) ",
                validFrom
            ),
            CodeListItem(
                "1.3",
                "Barnet flytter sammen med fosterforeldre til nytt bosted",
                validFrom
            ),
            CodeListItem(
                "2.1",
                "Avsluttet i henhold til plan ved oppstart av institusjonsoppholdet",
                validFrom
            ),
            CodeListItem(
                "2.2",
                "Institusjonen barnet bor i klarer ikke å dekke barnets behov (manglende kompetanse hos ansatte, beboersammensetning, fysiske forhold ved institusjonen osv)",
                validFrom
            ),
            CodeListItem(
                "2.3",
                "Barnet har behov for annen type plasseringstiltak (annen type institusjon, TFCO-fosterhjem, forsterket fosterhjem, osv. ",
                validFrom
            ),
            CodeListItem(
                "2.4",
                "Barnet blir myndig og velger selv å flytte ut",
                validFrom
            ),
            CodeListItem("2.5", "Barnet trekker samtykke", validFrom),
            CodeListItem("2.6", "Foreldre trekker samtykke", validFrom),
            CodeListItem("2.7", "Ikke medhold i fylkesnemnda", validFrom),
            CodeListItem(
                "2.8",
                "Uenighet mellom barnevernstjenesten og Bufetat om oppdragets omfang og/eller økonomi,",
                validFrom
            ),
            CodeListItem("2.9", "Andre grunner (krever presisering)", validFrom)
        )
    }
}
