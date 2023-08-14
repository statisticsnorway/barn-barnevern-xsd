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
import no.ssb.barn.TestUtils.MAX_DATE
import no.ssb.barn.TestUtils.MIN_DATE
import no.ssb.barn.TestUtils.VALID_DATE
import no.ssb.barn.TestUtils.buildBarnevernXml
import no.ssb.barn.toStreamSource
import no.ssb.barn.util.ValidationUtils.getSchemaValidator
import org.xml.sax.SAXException

class EttervernTypeTest : BehaviorSpec({

    Given("misc Ettervern XML") {

        /** make sure it's possible to make a valid test XML */
        When("valid XML, expect no exceptions") {
            shouldNotThrowAny {
                getSchemaValidator().validate(
                    buildBarnevernXml(
                        "<Ettervern Id=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" " +
                                "MigrertId=\"4242\" TilbudSendtDato=\"$VALID_DATE\"/>"
                    ).toStreamSource()
                )
            }
        }

        forAll(
            /** Id */

            row(
                "duplicate Id",
                "<Ettervern Id=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" TilbudSendtDato=\"$VALID_DATE\"/>" +
                        "<Ettervern Id=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" TilbudSendtDato=\"$VALID_DATE\"/>",
                "cvc-identity-constraint.4.1: Duplicate unique value [6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e] " +
                        "declared for identity constraint \"EttervernIdUnique\" of element \"Sak\"."
            ),

            row(
                "missing Id",
                "<Ettervern TilbudSendtDato=\"$VALID_DATE\"/>",
                "cvc-complex-type.4: Attribute 'Id' must appear on element 'Ettervern'."
            ),
            row(
                "empty Id",
                "<Ettervern Id=\"\" TilbudSendtDato=\"$VALID_DATE\"/>",
                EMPTY_ID_ERROR
            ),
            row(
                "invalid Id",
                "<Ettervern Id=\"42\" TilbudSendtDato=\"$VALID_DATE\"/>",
                INVALID_ID_ERROR
            ),

            /** MigrertId */
            row(
                "empty MigrertId",
                "<Ettervern Id=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" " +
                        "MigrertId=\"\" TilbudSendtDato=\"$VALID_DATE\"/>",
                "cvc-minLength-valid: Value '' with length = '0' is not facet-valid with respect to minLength '1' " +
                        "for type '#AnonType_MigrertId'."
            ),
            row(
                "too long MigrertId",
                "<Ettervern Id=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" " +
                        "MigrertId=\"${"a".repeat(37)}\" TilbudSendtDato=\"$VALID_DATE\"/>",
                "cvc-maxLength-valid: Value '${"a".repeat(37)}' with length = '37' is not facet-valid with " +
                        "respect to maxLength '36' for type '#AnonType_MigrertId'."
            ),

            /** TilbudSendtDato */
            row(
                "missing TilbudSendtDato",
                "<Ettervern Id=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" />",
                "cvc-complex-type.4: Attribute 'TilbudSendtDato' must appear on element 'Ettervern'."
            ),
            row(
                "empty TilbudSendtDato",
                "<Ettervern Id=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" TilbudSendtDato=\"\"/>",
                EMPTY_DATE_ERROR
            ),
            row(
                "invalid TilbudSendtDato",
                "<Ettervern Id=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" TilbudSendtDato=\"$INVALID_DATE\"/>",
                INVALID_DATE_FORMAT_ERROR
            ),
            row(
                "TilbudSendtDato too early",
                "<Ettervern Id=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" TilbudSendtDato=\"$INVALID_MIN_DATE_TOO_EARLY\"/>",
                "cvc-minInclusive-valid: Value '$INVALID_MIN_DATE_TOO_EARLY' is not facet-valid with respect to " +
                        "minInclusive '$MIN_DATE' for type '#AnonType_TilbudSendtDatoEttervernType'."
            ),
            row(
                "TilbudSendtDato too late",
                "<Ettervern Id=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" TilbudSendtDato=\"$INVALID_MAX_DATE_TOO_LATE\"/>",
                "cvc-maxInclusive-valid: Value '$INVALID_MAX_DATE_TOO_LATE' is not facet-valid with respect to " +
                        "maxInclusive '$MAX_DATE' for type '#AnonType_TilbudSendtDatoEttervernType'."
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