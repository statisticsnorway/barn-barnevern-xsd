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
import no.ssb.barn.TestUtils.VALID_DATE
import no.ssb.barn.TestUtils.buildBarnevernXml
import no.ssb.barn.toStreamSource
import no.ssb.barn.util.ValidationUtils.getSchemaValidatorV4
import org.xml.sax.SAXException

class PlanEvalueringTypeTest : BehaviorSpec({

    Given("misc PlanEvaluering XML") {

        /** make sure it's possible to make a valid test XML */
        When("valid XML, expect no exceptions") {
            shouldNotThrowAny {
                getSchemaValidatorV4().validate(
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
            )
        ) { description, partialXml, expectedError ->
            When(description) {
                val thrown = shouldThrow<SAXException> {
                    getSchemaValidatorV4().validate(buildEvalueringXml(partialXml).toStreamSource())
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