package no.ssb.barn.xsd

import io.kotest.assertions.throwables.shouldNotThrowAny
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.data.forAll
import io.kotest.data.row
import io.kotest.matchers.shouldBe
import no.ssb.barn.TestUtils.CLARIFICATION_MAX_LEN
import no.ssb.barn.TestUtils.INVALID_CLARIFICATION_ERROR
import no.ssb.barn.TestUtils.VALID_DATE
import no.ssb.barn.TestUtils.buildBarnevernXml
import no.ssb.barn.toStreamSource
import no.ssb.barn.util.ValidationUtils.getSchemaValidator
import org.xml.sax.SAXException

class SaksinnholdTypeTest : BehaviorSpec({

    Given("misc Saksinnhold XML") {

        /** make sure it's possible to make a valid test XML */
        When("valid XML, expect no exceptions") {
            shouldNotThrowAny {
                getSchemaValidator().validate(
                    buildXmlInTest(
                        "<Saksinnhold Kode=\"1\" Presisering=\"~Presisering~\"/>"
                    ).toStreamSource()
                )
            }
        }

        forAll(
            /** Kode */
            row(
                "missing Kode",
                "<Saksinnhold />",
                "cvc-complex-type.4: Attribute 'Kode' must appear on element 'Saksinnhold'."
            ),
            row(
                "empty Kode",
                "<Saksinnhold Kode=\"\" />",
                "cvc-minLength-valid: Value '' with length = '0' is not facet-valid with respect to " +
                        "minLength '1' for type '#AnonType_KodeSaksinnholdType'."
            ),
            row(
                "invalid Kode",
                "<Saksinnhold Kode=\"42\" />",
                "cvc-enumeration-valid: Value '42' is not facet-valid with respect to enumeration " +
                        "'[1, 2, 3, 4, 20, 21, 22, 23, 24, 28, 29, 31, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, " +
                        "25, 26, 27, 30, 18, 19]'. It must be a value from the enumeration."
            ),

            /** Presisering */
            row(
                "empty Presisering",
                "<Saksinnhold Kode=\"1\" Presisering=\"\"/>",
                "cvc-minLength-valid: Value '' with length = '0' is not facet-valid with respect " +
                        "to minLength '1' for type '#AnonType_Presisering'."
            ),
            row(
                "too long Presisering",
                "<Saksinnhold Kode=\"1\" Presisering=\"${"a".repeat(CLARIFICATION_MAX_LEN + 1)}\"/>",
                INVALID_CLARIFICATION_ERROR
            )
        ) { description, partialXml, expectedError ->
            When(description) {
                val thrown = shouldThrow<SAXException> {
                    getSchemaValidator().validate(buildXmlInTest(partialXml).toStreamSource())
                }

                Then("thrown should be as expected") {
                    thrown.message shouldBe expectedError
                }
            }
        }
    }
}) {
    companion object {
        private fun buildXmlInTest(saksinnholdXml: String): String = buildBarnevernXml(
            "<Melding Id=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" StartDato=\"$VALID_DATE\">" +
                    saksinnholdXml +
                    "</Melding>"
        )
    }
}
