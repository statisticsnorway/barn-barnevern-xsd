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

class FagsystemTypeTest : BehaviorSpec({

    Given("misc Fagsystem XML") {

        /** make sure it's possible to make a valid test XML */
        When("valid XML, expect no exceptions") {
            shouldNotThrowAny {
                getSchemaValidator().validate(
                    buildXmlInTest(
                        "<Fagsystem Leverandor=\"Modulus Barn\" Navn=\"Modulus Barn\" Versjon=\"1\" />"
                    ).toStreamSource()
                )
            }
        }

        forAll(
            /** Leverandor */
            row(
                "missing Leverandor",
                "<Fagsystem Navn=\"Modulus Barn\" Versjon=\"1\" />",
                "cvc-complex-type.4: Attribute 'Leverandor' must appear on element 'Fagsystem'."
            ),
            row(
                "empty Leverandor",
                "<Fagsystem Leverandor=\"\" Navn=\"Modulus Barn\" Versjon=\"1\" />",
                "cvc-minLength-valid: Value '' with length = '0' is not facet-valid with respect to " +
                        "minLength '3' for type '#AnonType_LeverandorFagsystemType'."
            ),
            row(
                "too short Leverandor",
                "<Fagsystem Leverandor=\"a\" Navn=\"Modulus Barn\" Versjon=\"1\" />",
                "cvc-minLength-valid: Value 'a' with length = '1' is not facet-valid with respect to " +
                        "minLength '3' for type '#AnonType_LeverandorFagsystemType'."
            ),
            row(
                "too long Leverandor",
                "<Fagsystem Leverandor=\"${"a".repeat(301)}\" Navn=\"Modulus Barn\" Versjon=\"1\" />",
                "cvc-maxLength-valid: Value '${"a".repeat(301)}' with length = '301' is not facet-valid " +
                        "with respect to maxLength '300' for type '#AnonType_LeverandorFagsystemType'."
            ),

            /** Navn */
            row(
                "missing Navn",
                "<Fagsystem Leverandor=\"Netcompany\" Versjon=\"1\" />",
                "cvc-complex-type.4: Attribute 'Navn' must appear on element 'Fagsystem'."
            ),
            row(
                "empty Navn",
                "<Fagsystem Leverandor=\"Netcompany\" Navn=\"\" Versjon=\"1\" />",
                "cvc-minLength-valid: Value '' with length = '0' is not facet-valid with respect to " +
                        "minLength '1' for type '#AnonType_NavnFagsystemType'."
            ),
            row(
                "too long Navn",
                "<Fagsystem Leverandor=\"Netcompany\" Navn=\"${"a".repeat(301)}\" Versjon=\"1\" />",
                "cvc-maxLength-valid: Value '${"a".repeat(301)}' with length = '301' is not facet-valid " +
                        "with respect to maxLength '300' for type '#AnonType_NavnFagsystemType'."
            ),

            /** Versjon */
            row(
                "missing Versjon",
                "<Fagsystem Leverandor=\"Netcompany\" Navn=\"Modulus Barn\" />",
                "cvc-complex-type.4: Attribute 'Versjon' must appear on element 'Fagsystem'."
            ),
            row(
                "empty Versjon",
                "<Fagsystem Leverandor=\"Netcompany\" Navn=\"Modulus Barn\" Versjon=\"\" />",
                "cvc-minLength-valid: Value '' with length = '0' is not facet-valid with respect to " +
                        "minLength '1' for type '#AnonType_VersjonFagsystemType'."
            ),
            row(
                "too long Versjon",
                "<Fagsystem Leverandor=\"Netcompany\" Navn=\"Modulus Barn\" Versjon=\"${"a".repeat(101)}\" />",
                "cvc-maxLength-valid: Value '${"a".repeat(101)}' with length = '101' is not " +
                        "facet-valid with respect to maxLength '100' for type '#AnonType_VersjonFagsystemType'."
            )
        ) { description, fagsystemXml, expectedError ->
            When(description) {
                val thrown = shouldThrow<SAXException> {
                    getSchemaValidator().validate(buildXmlInTest(fagsystemXml).toStreamSource())
                }

                Then("thrown should be as expected") {
                    thrown.message shouldBe expectedError
                }
            }
        }
    }
}) {
    companion object {
        private fun buildXmlInTest(fagsystemXml: String): String =
            "<Barnevern Id=\"236110fc-edba-4b86-87b3-d6bb945cbc76\" DatoUttrekk=\"2022-11-14T15:13:33+01:00\">" +
                    fagsystemXml +
                    "<Avgiver Organisasjonsnummer=\"999999999\" Kommunenummer=\"1234\" Kommunenavn=\"En kommune\" />" +
                    "<Sak Id=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" StartDato=\"2022-11-14\" Journalnummer=\"2022-00004\"/>" +
                    "</Barnevern>"
    }
}
