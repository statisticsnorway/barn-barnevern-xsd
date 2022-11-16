package no.ssb.barn.xsd

import io.kotest.assertions.throwables.shouldNotThrowAny
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.data.forAll
import io.kotest.data.row
import io.kotest.matchers.shouldBe
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
            /** Organisasjonsnummer */
            row(
                "missing Organisasjonsnummer",
                "<Avgiver Kommunenummer=\"1234\" Kommunenavn=\"En kommune\" />",
                "cvc-complex-type.4: Attribute 'Organisasjonsnummer' must appear on element 'Avgiver'."
            ),
            row(
                "empty Organisasjonsnummer",
                "<Avgiver Organisasjonsnummer=\"\" Kommunenummer=\"1234\" Kommunenavn=\"En kommune\" />",
                "cvc-pattern-valid: Value '' is not facet-valid with respect to pattern '\\d{9}' for " +
                        "type '#AnonType_OrganisasjonsnummerAvgiverType'."
            ),
            row(
                "invalid Organisasjonsnummer",
                "<Avgiver Organisasjonsnummer=\"1234\" Kommunenummer=\"1234\" Kommunenavn=\"En kommune\" />",
                "cvc-pattern-valid: Value '1234' is not facet-valid with respect to pattern '\\d{9}' for " +
                        "type '#AnonType_OrganisasjonsnummerAvgiverType'."
            ),

            /** Kommunenummer */
            row(
                "missing Kommunenummer",
                "<Avgiver Organisasjonsnummer=\"111111111\" Kommunenavn=\"En kommune\" />",
                "cvc-complex-type.4: Attribute 'Kommunenummer' must appear on element 'Avgiver'."
            ),
            row(
                "empty Kommunenummer",
                "<Avgiver Organisasjonsnummer=\"111111111\" Kommunenummer=\"\" Kommunenavn=\"En kommune\" />",
                "cvc-pattern-valid: Value '' is not facet-valid with respect to pattern '\\d{4}' for " +
                        "type '#AnonType_KommunenummerAvgiverType'."
            ),
            row(
                "invalid Kommunenummer",
                "<Avgiver Organisasjonsnummer=\"111111111\" Kommunenummer=\"42\" Kommunenavn=\"En kommune\" />",
                "cvc-pattern-valid: Value '42' is not facet-valid with respect to pattern '\\d{4}' for " +
                        "type '#AnonType_KommunenummerAvgiverType'."
            ),

            /** Kommunenavn */
            row(
                "missing Kommunenavn",
                "<Avgiver Organisasjonsnummer=\"111111111\" Kommunenummer=\"1234\" />",
                "cvc-complex-type.4: Attribute 'Kommunenavn' must appear on element 'Avgiver'."
            ),
            row(
                "empty Kommunenavn",
                "<Avgiver Organisasjonsnummer=\"111111111\" Kommunenummer=\"1234\" Kommunenavn=\"\" />",
                "cvc-minLength-valid: Value '' with length = '0' is not facet-valid with respect to " +
                        "minLength '1' for type '#AnonType_KommunenavnAvgiverType'."
            ),
            row(
                "too long Kommunenavn",
                "<Avgiver Organisasjonsnummer=\"111111111\" Kommunenummer=\"1234\" " +
                        "Kommunenavn=\"${"a".repeat(251)}\" />",
                "cvc-maxLength-valid: Value '${"a".repeat(251)}' with length = '251' is not facet-valid " +
                        "with respect to maxLength '250' for type '#AnonType_KommunenavnAvgiverType'."
            ),

            /** Bydelsnummer */
            row(
                "empty Bydelsnummer",
                "<Avgiver Organisasjonsnummer=\"111111111\" Kommunenummer=\"1234\" Kommunenavn=\"En kommune\" " +
                        "Bydelsnummer=\"\" Bydelsnavn=\"Bydel\" />",
                "cvc-pattern-valid: Value '' is not facet-valid with respect to pattern '\\d{2}' for " +
                        "type '#AnonType_BydelsnummerAvgiverType'."
            ),
            row(
                "too long Bydelsnummer",
                "<Avgiver Organisasjonsnummer=\"111111111\" Kommunenummer=\"1234\" Kommunenavn=\"En kommune\" " +
                        "Bydelsnummer=\"${"b".repeat(5)}\" Bydelsnavn=\"Bydel\" />",
                "cvc-pattern-valid: Value 'bbbbb' is not facet-valid with respect to pattern '\\d{2}' for " +
                        "type '#AnonType_BydelsnummerAvgiverType'."
            ),

            /** Bydelsnavn */
            row(
                "empty Bydelsnavn",
                "<Avgiver Organisasjonsnummer=\"111111111\" Kommunenummer=\"1234\" Kommunenavn=\"En kommune\" " +
                        "Bydelsnummer=\"11\" Bydelsnavn=\"\" />",
                "cvc-minLength-valid: Value '' with length = '0' is not facet-valid with respect to " +
                        "minLength '1' for type '#AnonType_BydelsnavnAvgiverType'."
            ),
            row(
                "too long Bydelsnavn",
                "<Avgiver Organisasjonsnummer=\"111111111\" Kommunenummer=\"1234\" Kommunenavn=\"En kommune\" " +
                        "Bydelsnummer=\"11\" Bydelsnavn=\"${"b".repeat(251)}\" />",
                "cvc-maxLength-valid: Value '${"b".repeat(251)}' with length = '251' is not " +
                        "facet-valid with respect to maxLength '250' for type '#AnonType_BydelsnavnAvgiverType'."
            )
        ) { description, avgiverXml, expectedError ->
            `when`(description) {
                val thrown = shouldThrow<SAXException> {
                    getSchemaValidator().validate(buildXmlInTest(avgiverXml).toStreamSource())
                }

                then("thrown should be as expected") {
                    thrown.message shouldBe expectedError
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
                    "<Sak Id=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" StartDato=\"2022-11-14\" Journalnummer=\"2022-00004\"/>" +
                    "</Barnevern>"
    }
}
