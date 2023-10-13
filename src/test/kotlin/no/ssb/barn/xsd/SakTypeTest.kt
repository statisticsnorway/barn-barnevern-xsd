package no.ssb.barn.xsd

import io.kotest.assertions.throwables.shouldNotThrowAny
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.data.forAll
import io.kotest.data.row
import io.kotest.matchers.shouldBe
import no.ssb.barn.TestUtils.EMPTY_DATE_ERROR
import no.ssb.barn.TestUtils.EMPTY_ID_ERROR
import no.ssb.barn.TestUtils.INVALID_DATE
import no.ssb.barn.TestUtils.INVALID_DATE_FORMAT_ERROR
import no.ssb.barn.TestUtils.INVALID_ID_ERROR
import no.ssb.barn.TestUtils.VALID_DATE
import no.ssb.barn.TestUtils.VALID_DATO_UTTREKK
import no.ssb.barn.toStreamSource
import no.ssb.barn.util.ValidationUtils.getSchemaValidator
import org.xml.sax.SAXException

class SakTypeTest : BehaviorSpec({

    Given("misc Sak XML") {

        forAll(
            row("POST"),
            row("PATCH"),
            row("PUT"),
            row("DELETE"),
            row("no verb")
        ) { verb ->
            /** make sure it's possible to make a valid test XML */
            When("valid XML with $verb, expect no exceptions") {

                val verbAttribute = when (verb) {
                    "no verb" -> ""
                    else -> "Verb=\"$verb\""
                }

                shouldNotThrowAny {
                    getSchemaValidator().validate(
                        buildXmlInTest(
                            "<Sak $verbAttribute " +
                                "Id=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" " +
                                "MigrertId=\"~MigrertId~\" " +
                                "StartDato=\"$VALID_DATE\" " +
                                "SluttDato=\"$VALID_DATE\" " +
                                "Avsluttet=\"true\" " +
                                "Journalnummer=\"00004\"" +
                                "/>"
                        ).toStreamSource()
                    )
                }
            }
        }

        forAll(
            /** Id */
            row(
                "missing Id",
                "<Sak StartDato=\"$VALID_DATE\" Journalnummer=\"00004\"/>",
                "cvc-complex-type.4: Attribute 'Id' must appear on element 'Sak'."
            ),
            row(
                "empty Id",
                "<Sak Id=\"\" StartDato=\"$VALID_DATE\" Journalnummer=\"00004\"/>",
                EMPTY_ID_ERROR
            ),
            row(
                "invalid Id",
                "<Sak Id=\"42\" StartDato=\"$VALID_DATE\" Journalnummer=\"00004\"/>",
                INVALID_ID_ERROR
            ),

            /** MigrertId */
            row(
                "empty MigrertId",
                "<Sak Id=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" MigrertId=\"\" StartDato=\"$VALID_DATE\" " +
                        "Journalnummer=\"00004\"/>",
                "cvc-minLength-valid: Value '' with length = '0' is not facet-valid with respect to minLength '1' " +
                        "for type '#AnonType_MigrertId'."
            ),
            row(
                "too long MigrertId",
                "<Sak Id=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" MigrertId=\"${"a".repeat(37)}\" " +
                        "StartDato=\"$VALID_DATE\" Journalnummer=\"00004\"/>",
                "cvc-maxLength-valid: Value '${"a".repeat(37)}' with length = '37' is not facet-valid with " +
                        "respect to maxLength '36' for type '#AnonType_MigrertId'."
            ),

            /** StartDato */
            row(
                "missing StartDato",
                "<Sak Id=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" Journalnummer=\"00004\"/>",
                "cvc-complex-type.4: Attribute 'StartDato' must appear on element 'Sak'."
            ),
            row(
                "empty StartDato",
                "<Sak Id=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" StartDato=\"\" Journalnummer=\"00004\"/>",
                EMPTY_DATE_ERROR
            ),
            row(
                "invalid StartDato",
                "<Sak Id=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" StartDato=\"$INVALID_DATE\" Journalnummer=\"00004\"/>",
                INVALID_DATE_FORMAT_ERROR
            ),

            /** SluttDato */
            row(
                "empty SluttDato",
                "<Sak Id=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" StartDato=\"$VALID_DATE\" SluttDato=\"\" " +
                        "Journalnummer=\"00004\"/>",
                EMPTY_DATE_ERROR
            ),
            row(
                "invalid SluttDato",
                "<Sak Id=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" StartDato=\"$VALID_DATE\" SluttDato=\"$INVALID_DATE\" " +
                        "Journalnummer=\"00004\"/>",
                INVALID_DATE_FORMAT_ERROR
            ),

            /** Avsluttet */
            row(
                "empty Avsluttet",
                "<Sak Id=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" StartDato=\"$VALID_DATE\" Avsluttet=\"\" " +
                        "Journalnummer=\"00004\"/>",
                "cvc-datatype-valid.1.2.1: '' is not a valid value for 'boolean'."
            ),
            row(
                "invalid Avsluttet",
                "<Sak Id=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" StartDato=\"$VALID_DATE\" Avsluttet=\"True\" " +
                        "Journalnummer=\"00004\"/>",
                "cvc-datatype-valid.1.2.1: 'True' is not a valid value for 'boolean'."
            ),

            /** Journalnummer */
            row(
                "missing Journalnummer",
                "<Sak Id=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" StartDato=\"$VALID_DATE\"/>",
                "cvc-complex-type.4: Attribute 'Journalnummer' must appear on element 'Sak'."
            ),
            row(
                "empty Journalnummer",
                "<Sak Id=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" StartDato=\"$VALID_DATE\" " +
                        "Journalnummer=\"\"/>",
                "cvc-minLength-valid: Value '' with length = '0' is not facet-valid with respect to minLength '1' " +
                        "for type '#AnonType_JournalnummerSakType'."
            ),
            row(
                "too long Journalnummer",
                "<Sak Id=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" StartDato=\"$VALID_DATE\" " +
                        "Journalnummer=\"${"a".repeat(37)}\"/>",
                "cvc-maxLength-valid: Value '${"a".repeat(37)}' with length = '37' is not facet-valid with " +
                        "respect to maxLength '36' for type '#AnonType_JournalnummerSakType'."
            ),

            /** Verb */
            row(
                "empty verb",
                "<Sak Verb=\"\" Id=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" StartDato=\"$VALID_DATE\" " +
                "Journalnummer=\"00004\"/>",
                "cvc-pattern-valid: Value '' is not facet-valid with respect to pattern 'POST|PATCH|PUT|DELETE' " +
                    "for type '#AnonType_VerbSakType'."
            ),
            row(
                "invalid verb",
                "<Sak Verb=\"PPOST\" Id=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" StartDato=\"$VALID_DATE\" " +
                    "Journalnummer=\"00004\"/>",
                "cvc-pattern-valid: Value 'PPOST' is not facet-valid with respect to pattern " +
                    "'POST|PATCH|PUT|DELETE' for type '#AnonType_VerbSakType'."
            )
        ) { description, sakElement, expectedError ->
            When(description) {
                val thrown = shouldThrow<SAXException> {
                    getSchemaValidator().validate(buildXmlInTest(sakElement).toStreamSource())
                }

                Then("thrown should be as expected") {
                    thrown.message shouldBe expectedError
                }
            }
        }
    }
}) {
    companion object {
        private fun buildXmlInTest(sakElement: String): String =
            "<Barnevern Id=\"236110fc-edba-4b86-87b3-d6bb945cbc76\" DatoUttrekk=\"$VALID_DATO_UTTREKK\">" +
                    "<Fagsystem Leverandor=\"Netcompany\" Navn=\"Modulus Barn\" Versjon=\"1\" />" +
                    "<Avgiver Organisasjonsnummer=\"999999999\" Kommunenummer=\"1234\" Kommunenavn=\"En kommune\"/>" +
                    sakElement +
                    "</Barnevern>"
    }
}
