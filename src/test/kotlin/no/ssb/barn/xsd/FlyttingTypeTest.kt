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
import no.ssb.barn.TestUtils.buildBarnevernXml
import no.ssb.barn.toStreamSource
import no.ssb.barn.util.ValidationUtils.getSchemaValidatorV4
import org.xml.sax.SAXException

class FlyttingTypeTest : BehaviorSpec({

    Given("misc Flytting XML") {

        /** make sure it's possible to make a valid test XML */
        When("valid XML, expect no exceptions") {
            shouldNotThrowAny {
                getSchemaValidatorV4().validate(
                    buildBarnevernXml(
                        "<Flytting Id=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" MigrertId=\"1234\" " +
                                "SluttDato=\"$VALID_DATE\" ErSlettet=\"true\">" +
                                "<ArsakFra Kode=\"1.1.1\" />" +
                                "<FlyttingTil Kode=\"1\" />" +
                                "</Flytting>"

                    ).toStreamSource()
                )
            }
        }

        forAll(
            /** Id */

            row(
                "duplicate Id",
                "<Flytting Id=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" " +
                        "SluttDato=\"$VALID_DATE\">" +
                        "<ArsakFra Kode=\"1.1.1\" />" +
                        "<FlyttingTil Kode=\"1\" />" +
                        "</Flytting> " +
                        "<Flytting Id=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" " +
                        "SluttDato=\"$VALID_DATE\">" +
                        "<ArsakFra Kode=\"1.1.1\" />" +
                        "<FlyttingTil Kode=\"1\" />" +
                        "</Flytting>",
                "cvc-identity-constraint.4.1: Duplicate unique value [6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e] " +
                        "declared for identity constraint \"FlyttingIdUnique\" of element \"Sak\"."
            ),

            row(
                "missing Id",
                "<Flytting SluttDato=\"$VALID_DATE\">",
                "cvc-complex-type.4: Attribute 'Id' must appear on element 'Flytting'."
            ),
            row(
                "empty Id",
                "<Flytting Id=\"\" SluttDato=\"$VALID_DATE\">",
                EMPTY_ID_ERROR
            ),
            row(
                "invalid Id",
                "<Flytting Id=\"42\" SluttDato=\"$VALID_DATE\">",
                INVALID_ID_ERROR
            ),

            /** MigrertId */
            row(
                "empty MigrertId",
                "<Flytting Id=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" MigrertId=\"\" SluttDato=\"$VALID_DATE\">",
                "cvc-minLength-valid: Value '' with length = '0' is not facet-valid with respect to minLength '1' " +
                        "for type '#AnonType_MigrertId'."
            ),
            row(
                "too long MigrertId",
                "<Flytting Id=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" MigrertId=\"${"a".repeat(37)}\" " +
                        "SluttDato=\"$VALID_DATE\">",
                "cvc-maxLength-valid: Value '${"a".repeat(37)}' with length = '37' is not facet-valid with " +
                        "respect to maxLength '36' for type '#AnonType_MigrertId'."
            ),

            /** SluttDato */
            row(
                "missing SluttDato",
                "<Flytting Id=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\">",
                "cvc-complex-type.4: Attribute 'SluttDato' must appear on element 'Flytting'."
            ),
            row(
                "empty SluttDato",
                "<Flytting Id=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" SluttDato=\"\">",
                EMPTY_DATE_ERROR
            ),
            row(
                "invalid SluttDato",
                "<Flytting Id=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" SluttDato=\"$INVALID_DATE\">",
                INVALID_DATE_FORMAT_ERROR
            ),

            /** ArsakFra */
            row(
                "missing ArsakFra",
                "<Flytting Id=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" SluttDato=\"$VALID_DATE\" />",
                "cvc-complex-type.2.4.b: The content of element 'Flytting' is not complete. One of '{ArsakFra}' is expected."
            ),

            /** FlyttingTil */
            row(
                "missing FlyttingTil",
                "<Flytting Id=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" MigrertId=\"1234\" " +
                        "SluttDato=\"$VALID_DATE\">" +
                        "<ArsakFra Kode=\"1.1.1\" />" +
                        "</Flytting>",
                "cvc-complex-type.2.4.b: The content of element 'Flytting' is not complete. One of '{FlyttingTil}' is expected."
            )
        ) { description, partialXml, expectedError ->
            When(description) {
                val thrown = shouldThrow<SAXException> {
                    getSchemaValidatorV4().validate(buildBarnevernXml(partialXml).toStreamSource())
                }

                Then("thrown should be as expected") {
                    thrown.message shouldBe expectedError
                }
            }
        }
    }
})