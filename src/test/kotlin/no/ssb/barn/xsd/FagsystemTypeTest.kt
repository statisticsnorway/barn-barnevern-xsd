package no.ssb.barn.xsd

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.data.forAll
import io.kotest.data.row
import io.kotest.matchers.string.shouldStartWith
import no.ssb.barn.util.ValidationUtils.getSchemaValidator
import org.xml.sax.SAXException
import java.io.StringReader
import javax.xml.transform.stream.StreamSource

class FagsystemTypeTest : BehaviorSpec({

    given("misc invalid Fagsystem XML") {

        forAll(
            row(
                "missing Leverandor",
                "<Fagsystem Navn=\"Modulus Barn\" Versjon=\"1\" />"
            ),
            row(
                "empty Leverandor",
                "<Fagsystem Leverandor=\"\" Navn=\"Modulus Barn\" Versjon=\"1\" />"
            ),
            row(
                "too short Leverandor",
                "<Fagsystem Leverandor=\"a\" Navn=\"Modulus Barn\" Versjon=\"1\" />"
            ),
            row(
                "too long Leverandor",
                "<Fagsystem Leverandor=\"${"a".repeat(301)}\" Navn=\"Modulus Barn\" Versjon=\"1\" />"
            ),

            row(
                "missing Navn",
                "<Fagsystem Leverandor=\"Netcompany\" Versjon=\"1\" />"
            ),
            row(
                "empty Navn",
                "<Fagsystem Leverandor=\"Netcompany\" Navn=\"\" Versjon=\"1\" />"
            ),
            row(
                "too long Navn",
                "<Fagsystem Leverandor=\"Netcompany\" Navn=\"${"a".repeat(301)}\" Versjon=\"1\" />"
            ),

            row(
                "missing Versjon",
                "<Fagsystem Leverandor=\"Netcompany\" Navn=\"Modulus Barn\" />"
            ),
            row(
                "empty Versjon",
                "<Fagsystem Leverandor=\"Netcompany\" Navn=\"Modulus Barn\" Versjon=\"\" />"
            ),
            row(
                "too long Versjon",
                "<Fagsystem Leverandor=\"Netcompany\" Navn=\"Modulus Barn\" Versjon=\"${"a".repeat(101)}\" />"
            )
        ) { description, fagsystemXml ->
            `when`(description) {
                val thrown = shouldThrow<SAXException> {
                    getSchemaValidator().validate(StreamSource(StringReader(buildXmlInTest(fagsystemXml))))
                }

                then("thrown should be as expected") {
                    thrown.message shouldStartWith "cvc-"
                }
            }
        }
    }
}) {
    companion object {
        fun buildXmlInTest(fagsystemXml: String): String =
            "<Barnevern Id=\"236110fc-edba-4b86-87b3-d6bb945cbc76\" DatoUttrekk=\"2022-11-14T15:13:33.1077852+01:00\">" +
                    fagsystemXml +
                    "<Avgiver Organisasjonsnummer=\"\" Kommunenummer=\"1234\" Kommunenavn=\"En kommune\" />" +
                    "<Sak Id=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" StartDato=\"2022-11-14\" Journalnummer=\"2022-00004\">" +
                    "<Melding Id=\"e16bec92-70fe-4313-957b-81430aced812\" StartDato=\"2022-11-14\">" +
                    "<Melder Kode=\"\" /></Melding></Sak></Barnevern>"
    }
}
