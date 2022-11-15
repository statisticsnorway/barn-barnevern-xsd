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

class AvgiverTypeTest : BehaviorSpec({

    given("misc invalid Avgiver XML") {

        forAll(
            row(
                "empty Organisasjonsnummer",
                "<Avgiver Organisasjonsnummer=\"\" Kommunenummer=\"1234\" Kommunenavn=\"En kommune\" />"
            ),
            row(
                "invalid Organisasjonsnummer",
                "<Avgiver Organisasjonsnummer=\"1234\" Kommunenummer=\"1234\" Kommunenavn=\"En kommune\" />"
            ),

            row(
                "empty Kommunenummer",
                "<Avgiver Organisasjonsnummer=\"111111111\" Kommunenummer=\"\" Kommunenavn=\"En kommune\" />"
            ),
            row(
                "invalid Kommunenummer",
                "<Avgiver Organisasjonsnummer=\"111111111\" Kommunenummer=\"42\" Kommunenavn=\"En kommune\" />"
            ),

            row(
                "empty Kommunenavn",
                "<Avgiver Organisasjonsnummer=\"111111111\" Kommunenummer=\"1234\" Kommunenavn=\"\" />"
            ),
            row(
                "too long Kommunenavn",
                "<Avgiver Organisasjonsnummer=\"111111111\" Kommunenummer=\"1234\" Kommunenavn=\"${"a".repeat(251)}\" />"
            ),

            row(
                "empty Bydelsnummer",
                "<Avgiver Organisasjonsnummer=\"111111111\" Kommunenummer=\"1234\" Kommunenavn=\"En kommune\" Bydelsnummer=\"\" Bydelsnavn=\"Digi Oslo kommune Bydel 01\" />"
            ),
            row(
                "too long Bydelsnummer",
                "<Avgiver Organisasjonsnummer=\"111111111\" Kommunenummer=\"1234\" Kommunenavn=\"En kommune\" Bydelsnummer=\"111\" Bydelsnavn=\"${"b".repeat(251)}\" />"
            ),

            row(
                "empty Bydelsnavn",
                "<Avgiver Organisasjonsnummer=\"111111111\" Kommunenummer=\"1234\" Kommunenavn=\"En kommune\" Bydelsnummer=\"11\" Bydelsnavn=\"\" />"
            ),
            row(
                "too long Bydelsnavn",
                "<Avgiver Organisasjonsnummer=\"111111111\" Kommunenummer=\"1234\" Kommunenavn=\"En kommune\" Bydelsnummer=\"11\" Bydelsnavn=\"${"b".repeat(251)}\" />"
            )
        ) { description, avgiverXml ->
            `when`(description) {
                val thrown = shouldThrow<SAXException> {
                    getSchemaValidator().validate(StreamSource(StringReader(buildXmlInTest(avgiverXml))))
                }

                then("thrown should be as expected") {
                    thrown.message shouldStartWith  "cvc-"
                }
            }
        }
    }
}) {
    companion object {
        fun buildXmlInTest(avgiverXml: String): String =
            "<Barnevern Id=\"236110fc-edba-4b86-87b3-d6bb945cbc76\" DatoUttrekk=\"2022-11-14T15:13:33.1077852+01:00\">" +
                    "<Fagsystem Leverandor=\"Netcompany\" Navn=\"Modulus Barn\" Versjon=\"1\" />" +
                    avgiverXml +
                    "<Sak Id=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" StartDato=\"2022-11-14\" Journalnummer=\"2022-00004\">" +
                    "<Melding Id=\"e16bec92-70fe-4313-957b-81430aced812\" StartDato=\"2022-11-14\"><Melder Kode=\"\" />" +
                    "</Melding></Sak></Barnevern>"
    }
}
