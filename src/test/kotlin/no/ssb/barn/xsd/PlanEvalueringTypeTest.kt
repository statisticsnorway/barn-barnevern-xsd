package no.ssb.barn.xsd

import io.kotest.assertions.throwables.shouldNotThrowAny
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.data.forAll
import io.kotest.data.row
import io.kotest.matchers.shouldBe
import no.ssb.barn.TestUtils.EMPTY_DATE_ERROR
import no.ssb.barn.TestUtils.INVALID_DATE
import no.ssb.barn.TestUtils.INVALID_DATE_FORMAT_ERROR
import no.ssb.barn.TestUtils.INVALID_MAX_DATE_TOO_LATE
import no.ssb.barn.TestUtils.INVALID_MIN_DATE_TOO_EARLY
import no.ssb.barn.TestUtils.MAX_DATE
import no.ssb.barn.TestUtils.MIN_DATE
import no.ssb.barn.TestUtils.VALID_DATE
import no.ssb.barn.TestUtils.buildBarnevernXml
import no.ssb.barn.toStreamSource
import no.ssb.barn.util.ValidationUtils.getSchemaValidator
import org.xml.sax.SAXException

class PlanEvalueringTypeTest : BehaviorSpec({

    Given("misc PlanEvaluering XML") {

        /** make sure it's possible to make a valid test XML */
        When("valid XML, expect no exceptions") {
            shouldNotThrowAny {
                getSchemaValidator().validate(
                    buildEvalueringXml(
                        "<Evaluering UtfortDato=\"$VALID_DATE\" />"
                    ).toStreamSource()
                )
            }
        }

        forAll(
            /** UtfortDato */
            row(
                "missing UtfortDato",
                "<Evaluering />",
                "cvc-complex-type.4: Attribute 'UtfortDato' must appear on element 'Evaluering'."
            ),
            row(
                "empty UtfortDato",
                "<Evaluering UtfortDato=\"\" />",
                EMPTY_DATE_ERROR
            ),
            row(
                "invalid UtfortDato",
                "<Evaluering UtfortDato=\"$INVALID_DATE\" />",
                INVALID_DATE_FORMAT_ERROR
            ),
            row(
                "UtfortDato too early",
                "<Evaluering UtfortDato=\"$INVALID_MIN_DATE_TOO_EARLY\" />",
               "cvc-minInclusive-valid: Value '$INVALID_MIN_DATE_TOO_EARLY' is not facet-valid with respect to " +
                        "minInclusive '$MIN_DATE' for type '#AnonType_UtfortDatoEvalueringPlanType'."
            ),
            row(
                "UtfortDato too late",
                "<Evaluering UtfortDato=\"$INVALID_MAX_DATE_TOO_LATE\" />",
                "cvc-maxInclusive-valid: Value '$INVALID_MAX_DATE_TOO_LATE' is not facet-valid with respect to " +
                        "maxInclusive '$MAX_DATE' for type '#AnonType_UtfortDatoEvalueringPlanType'."
            )
        ) { description, partialXml, expectedError ->
            When(description) {
                val thrown = shouldThrow<SAXException> {
                    getSchemaValidator().validate(buildEvalueringXml(partialXml).toStreamSource())
                }

                Then("thrown should be as expected") {
                    thrown.message shouldBe expectedError
                }
            }
        }
    }
}) {
    companion object {
        private fun buildEvalueringXml(innerXml: String): String = buildBarnevernXml(
            "<Plan Id=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" " +
                    "StartDato=\"$VALID_DATE\" Plantype=\"1\">" +
                    innerXml +
                    "</Plan>"
        )
    }
}