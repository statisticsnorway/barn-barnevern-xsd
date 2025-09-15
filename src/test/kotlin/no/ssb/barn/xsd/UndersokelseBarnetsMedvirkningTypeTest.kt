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

class UndersokelseBarnetsMedvirkningTypeTest : BehaviorSpec({

    Given("valid UndersokelseBarnetsMedvirkningType XML") {
        /** make sure it's possible to make a valid test XML */
        val validXML = "<BarnetsMedvirkning>" +
                "<StartDato>$VALID_DATE</StartDato>" +
                "<BarnetsMedvirkningType>$VALID_PARTICIPATION_TYPE</BarnetsMedvirkningType>" +
                "</BarnetsMedvirkning>"

        Then("should not throw any exceptions") {
            shouldNotThrowAny {
                getSchemaValidatorV4().validate(
                    buildUndersokelseXml(validXML).toStreamSource()
                )
            }
        }
    }

    Given("misc invalid UndersokelseBarnetsMedvirkningType XML") {
        forAll(
            /** StartDato */
            row(
                "missing StartDato",
                "<BarnetsMedvirkning><BarnetsMedvirkningType>$VALID_PARTICIPATION_TYPE</BarnetsMedvirkningType></BarnetsMedvirkning>",
                "cvc-complex-type.2.4.a: Invalid content was found starting with element 'BarnetsMedvirkningType'. One of '{StartDato}' is expected."
            ),
            row(
                "empty StartDato",
                "<BarnetsMedvirkning><StartDato></StartDato><BarnetsMedvirkningType>$VALID_PARTICIPATION_TYPE</BarnetsMedvirkningType></BarnetsMedvirkning>",
                EMPTY_DATE_ERROR
            ),
            row(
                "invalid StartDato",
                "<BarnetsMedvirkning><StartDato>$INVALID_DATE</StartDato><BarnetsMedvirkningType>$VALID_PARTICIPATION_TYPE</BarnetsMedvirkningType></BarnetsMedvirkning>",
                INVALID_DATE_FORMAT_ERROR
            ),

            /** BarnetsMedvirkningType */
            row(
                "missing BarnetsMedvirkningType",
                "<BarnetsMedvirkning><StartDato>$VALID_DATE</StartDato></BarnetsMedvirkning>",
                "cvc-complex-type.2.4.b: The content of element 'BarnetsMedvirkning' is not complete. One of '{BarnetsMedvirkningType}' is expected."
            ),
            row(
                "empty MedvirkningType",
                "<BarnetsMedvirkning><StartDato>$VALID_DATE</StartDato><BarnetsMedvirkningType></BarnetsMedvirkningType></BarnetsMedvirkning>",
                "cvc-length-valid: Value '' with length = '0' is not facet-valid with respect to length '3' for type '#AnonType_BarnetsMedvirkningTypeBarnetsMedvirkningType'."
            ),
            row(
                "invalid MedvirkningType",
                "<BarnetsMedvirkning><StartDato>$VALID_DATE</StartDato><BarnetsMedvirkningType>XXX</BarnetsMedvirkningType></BarnetsMedvirkning>",
                "cvc-enumeration-valid: Value 'XXX' is not facet-valid with respect to enumeration '[1.1, 1.2, 1.3, 1.4, 2.1, 2.2]'. It must be a value from the enumeration."
            ),

            ) { description, partialXml, expectedError ->
            When(description) {
                val thrown = shouldThrow<SAXException> {
                    getSchemaValidatorV4().validate(
                        buildUndersokelseXml(
                            partialXml
                        ).toStreamSource()
                    )
                }

                Then("thrown should be as expected") {
                    thrown.message shouldBe expectedError
                }
            }
        }
    }
}) {
    companion object {
        const val VALID_PARTICIPATION_TYPE = "1.1"

        private fun buildUndersokelseXml(undersokelseBarnetsMedvirkningXml: String): String =
            buildBarnevernXml(
                "<Undersokelse Id=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" StartDato=\"$VALID_DATE\">" +
                        undersokelseBarnetsMedvirkningXml +
                        "</Undersokelse>"
            )
    }
}