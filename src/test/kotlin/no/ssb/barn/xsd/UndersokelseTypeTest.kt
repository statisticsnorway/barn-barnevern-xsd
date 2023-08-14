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
import no.ssb.barn.TestUtils.INVALID_MAX_DATE_TOO_LATE
import no.ssb.barn.TestUtils.INVALID_MIN_DATE_TOO_EARLY
import no.ssb.barn.TestUtils.START_DATE_TOO_EARLY_ERROR
import no.ssb.barn.TestUtils.START_DATE_TOO_LATE_ERROR
import no.ssb.barn.TestUtils.VALID_DATE
import no.ssb.barn.TestUtils.buildBarnevernXml
import no.ssb.barn.toStreamSource
import no.ssb.barn.util.ValidationUtils.getSchemaValidator
import org.xml.sax.SAXException

class UndersokelseTypeTest : BehaviorSpec({

    Given("misc Undersokelse XML") {

        /** make sure it's possible to make a valid test XML */
        When("valid XML, expect no exceptions") {
            shouldNotThrowAny {
                getSchemaValidator().validate(
                    buildBarnevernXml(
                        "<Undersokelse Id=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" MigrertId=\"1234\" " +
                                "StartDato=\"$VALID_DATE\"/>"
                    ).toStreamSource()
                )
            }
        }

        forAll(
            /** Id */

            row(
                "duplicate Id",
                "<Undersokelse Id=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" " +
                        "StartDato=\"$VALID_DATE\"/> " +
                        "<Undersokelse Id=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" " +
                        "StartDato=\"$VALID_DATE\"/>",
                "cvc-identity-constraint.4.1: Duplicate unique value [6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e] " +
                        "declared for identity constraint \"UndersokelseIdUnique\" of element \"Sak\"."
            ),
            row(
                "missing Id",
                "<Undersokelse StartDato=\"$VALID_DATE\"/>",
                "cvc-complex-type.4: Attribute 'Id' must appear on element 'Undersokelse'."
            ),
            row(
                "empty Id",
                "<Undersokelse Id=\"\" StartDato=\"$VALID_DATE\"/>",
                EMPTY_ID_ERROR
            ),
            row(
                "invalid Id",
                "<Undersokelse Id=\"42\" StartDato=\"$VALID_DATE\"/>",
                INVALID_ID_ERROR
            ),

            /** MigrertId */
            row(
                "empty MigrertId",
                "<Undersokelse Id=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" MigrertId=\"\" StartDato=\"$VALID_DATE\"/>",
                "cvc-minLength-valid: Value '' with length = '0' is not facet-valid with respect to minLength '1' " +
                        "for type '#AnonType_MigrertId'."
            ),
            row(
                "too long MigrertId",
                "<Undersokelse Id=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" MigrertId=\"${"a".repeat(37)}\" " +
                        "StartDato=\"$VALID_DATE\"/>",
                "cvc-maxLength-valid: Value '${"a".repeat(37)}' with length = '37' is not facet-valid with " +
                        "respect to maxLength '36' for type '#AnonType_MigrertId'."
            ),

            /** StartDato */
            row(
                "missing StartDato",
                "<Undersokelse Id=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\"/>",
                "cvc-complex-type.4: Attribute 'StartDato' must appear on element 'Undersokelse'."
            ),
            row(
                "empty StartDato",
                "<Undersokelse Id=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" StartDato=\"\"/>",
                EMPTY_DATE_ERROR
            ),
            row(
                "invalid StartDato",
                "<Undersokelse Id=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" StartDato=\"$INVALID_DATE\"/>",
                INVALID_DATE_FORMAT_ERROR
            ),
            row(
                "StartDato too early",
                "<Undersokelse Id=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" StartDato=\"$INVALID_MIN_DATE_TOO_EARLY\"/>",
                START_DATE_TOO_EARLY_ERROR
            ),
            row(
                "StartDato too late",
                "<Undersokelse Id=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" StartDato=\"$INVALID_MAX_DATE_TOO_LATE\"/>",
                START_DATE_TOO_LATE_ERROR
            )
        ) { description, partialXml, expectedError ->
            When(description) {
                val thrown = shouldThrow<SAXException> {
                    getSchemaValidator().validate(buildBarnevernXml(partialXml).toStreamSource())
                }

                Then("thrown should be as expected") {
                    thrown.message shouldBe expectedError
                }
            }
        }
    }
})