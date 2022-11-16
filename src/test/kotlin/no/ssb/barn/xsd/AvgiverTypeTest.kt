package no.ssb.barn.xsd

import io.kotest.assertions.throwables.shouldNotThrowAny
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.data.forAll
import io.kotest.data.row
import io.kotest.matchers.string.shouldStartWith
import no.ssb.barn.toStreamSource
import no.ssb.barn.util.ValidationUtils.getSchemaValidator
import org.xml.sax.SAXException

class AvgiverTypeTest : BehaviorSpec({

    given("misc Avgiver XML") {

        /** make sure it's possible to make a valid test XML */
        `when`("valid XML, no exceptions are expected") {
            shouldNotThrowAny {
                getSchemaValidator().validate(
                    buildXmlInTest(
                        "<Avgiver Organisasjonsnummer=\"111111111\" " +
                                "Kommunenummer=\"1234\" Kommunenavn=\"~Kommunenavn~\" />"
                    ).toStreamSource()
                )
            }
        }

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
                "<Avgiver Organisasjonsnummer=\"111111111\" Kommunenummer=\"1234\" " +
                        "Kommunenavn=\"${"a".repeat(251)}\" />"
            ),

            row(
                "empty Bydelsnummer",
                "<Avgiver Organisasjonsnummer=\"111111111\" Kommunenummer=\"1234\" Kommunenavn=\"En kommune\" " +
                        "Bydelsnummer=\"\" Bydelsnavn=\"Bydel\" />"
            ),
            row(
                "too long Bydelsnummer",
                "<Avgiver Organisasjonsnummer=\"111111111\" Kommunenummer=\"1234\" Kommunenavn=\"En kommune\" " +
                        "Bydelsnummer=\"${"b".repeat(5)}\" Bydelsnavn=\"Bydel\" />"
            ),

            row(
                "empty Bydelsnavn",
                "<Avgiver Organisasjonsnummer=\"111111111\" Kommunenummer=\"1234\" Kommunenavn=\"En kommune\" " +
                        "Bydelsnummer=\"11\" Bydelsnavn=\"\" />"
            ),
            row(
                "too long Bydelsnavn",
                "<Avgiver Organisasjonsnummer=\"111111111\" Kommunenummer=\"1234\" Kommunenavn=\"En kommune\" " +
                        "Bydelsnummer=\"11\" Bydelsnavn=\"${"b".repeat(251)}\" />"
            )
        ) { description, avgiverXml ->
            `when`(description) {
                val thrown = shouldThrow<SAXException> {
                    getSchemaValidator().validate(buildXmlInTest(avgiverXml).toStreamSource())
                }

                then("thrown should be as expected") {
                    thrown.message shouldStartWith "cvc-"
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
                    "</Sak></Barnevern>"
    }
}
