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

class PlanTypeTest : BehaviorSpec({

    Given("misc Plan XML") {

        /** make sure it's possible to make a valid test XML */
        When("valid XML, expect no exceptions") {
            shouldNotThrowAny {
                getSchemaValidator().validate(buildBarnevernXml(
                    "<Plan Id=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" MigrertId=\"1234\" " +
                            "StartDato=\"$VALID_DATE\" Plantype=\"1\"/>").toStreamSource())
            }
        }

        forAll(
            /** Id */

            row(
                "duplicate Id",
                "<Plan Id=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" " +
                        "StartDato=\"$VALID_DATE\" Plantype=\"1\"/> " +
                        "<Plan Id=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" " +
                        "StartDato=\"$VALID_DATE\" Plantype=\"1\"/>",
                "cvc-identity-constraint.4.1: Duplicate unique value [6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e] " +
                        "declared for identity constraint \"PlanIdUnique\" of element \"Sak\"."
            ),

            row(
                "missing Id",
                "<Plan StartDato=\"$VALID_DATE\" Plantype=\"1\" />",
                "cvc-complex-type.4: Attribute 'Id' must appear on element 'Plan'."
            ),
            row(
                "empty Id",
                "<Plan Id=\"\" StartDato=\"$VALID_DATE\" Plantype=\"1\" />",
                EMPTY_ID_ERROR
            ),
            row(
                "invalid Id",
                "<Plan Id=\"42\" StartDato=\"$VALID_DATE\" Plantype=\"1\" />",
                INVALID_ID_ERROR
            ),

            /** MigrertId */
            row(
                "empty MigrertId",
                "<Plan Id=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" MigrertId=\"\" " +
                        "StartDato=\"$VALID_DATE\" Plantype=\"1\" />",
                "cvc-minLength-valid: Value '' with length = '0' is not facet-valid with respect to minLength '1' " +
                        "for type '#AnonType_MigrertId'."
            ),
            row(
                "too long MigrertId",
                "<Plan Id=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" MigrertId=\"${"a".repeat(37)}\" " +
                        "StartDato=\"$VALID_DATE\" Plantype=\"1\" />",
                "cvc-maxLength-valid: Value '${"a".repeat(37)}' with length = '37' is not facet-valid with " +
                        "respect to maxLength '36' for type '#AnonType_MigrertId'."
            ),

            /** StartDato */
            row(
                "missing StartDato",
                "<Plan Id=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" Plantype=\"1\" />",
                "cvc-complex-type.4: Attribute 'StartDato' must appear on element 'Plan'."
            ),
            row(
                "empty StartDato",
                "<Plan Id=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" StartDato=\"\" Plantype=\"1\" />",
                EMPTY_DATE_ERROR
            ),
            row(
                "invalid StartDato",
                "<Plan Id=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" StartDato=\"$INVALID_DATE\" Plantype=\"1\" />",
                INVALID_DATE_FORMAT_ERROR
            ),
            row(
                "StartDato too early",
                "<Plan Id=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" StartDato=\"$INVALID_MIN_DATE_TOO_EARLY\" Plantype=\"1\" />",
                START_DATE_TOO_EARLY_ERROR
            ),
            row(
                "StartDato too late",
                "<Plan Id=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" StartDato=\"$INVALID_MAX_DATE_TOO_LATE\" Plantype=\"1\" />",
                START_DATE_TOO_LATE_ERROR
            ),

            /** Plantype */
            row(
                "missing Plantype",
                "<Plan Id=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" StartDato=\"$VALID_DATE\" />",
                "cvc-complex-type.4: Attribute 'Plantype' must appear on element 'Plan'."
            ),
            row(
                "empty Plantype",
                "<Plan Id=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" StartDato=\"$VALID_DATE\" Plantype=\"\" />",
                "cvc-minLength-valid: Value '' with length = '0' is not facet-valid with respect to " +
                        "minLength '1' for type '#AnonType_PlantypePlanType'."
            ),
            row(
                "invalid Plantype",
                "<Plan Id=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" StartDato=\"$VALID_DATE\" Plantype=\"42\" />",
                "cvc-enumeration-valid: Value '42' is not facet-valid with respect to " +
                        "enumeration '[1, 2, 3, 4, 5, 6, 7, 8]'. It must be a value from the enumeration."
            ),
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