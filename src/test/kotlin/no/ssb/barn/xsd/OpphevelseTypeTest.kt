package no.ssb.barn.xsd

import io.kotest.assertions.throwables.shouldNotThrowAny
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.data.forAll
import io.kotest.data.row
import io.kotest.matchers.shouldBe
import no.ssb.barn.TestUtils.CLARIFICATION_MAX_LEN
import no.ssb.barn.TestUtils.INVALID_CLARIFICATION_ERROR
import no.ssb.barn.TestUtils.LOVHJEMMEL_XML
import no.ssb.barn.TestUtils.buildBarnevernXml
import no.ssb.barn.toStreamSource
import no.ssb.barn.util.ValidationUtils.getSchemaValidator
import org.xml.sax.SAXException

class OpphevelseTypeTest : BehaviorSpec({

    Given("misc Opphevelse XML") {

        /** make sure it's possible to make a valid test XML */
        When("valid XML, expect no exceptions") {
            shouldNotThrowAny {
                getSchemaValidator().validate(
                    buildXmlInTest(
                        "<Opphevelse Kode=\"1\" Presisering=\"~Presisering~\"/>"
                    ).toStreamSource()
                )
            }
        }

        forAll(
            /** Kode */
            row(
                "missing Kode",
                "<Opphevelse />",
                "cvc-complex-type.4: Attribute 'Kode' must appear on element 'Opphevelse'."
            ),
            row(
                "empty Kode",
                "<Opphevelse Kode=\"\" />",
                "cvc-minLength-valid: Value '' with length = '0' is not facet-valid with respect to " +
                        "minLength '1' for type '#AnonType_KodeOpphevelseType'."
            ),
            row(
                "invalid Kode",
                "<Opphevelse Kode=\"42\" />",
                "cvc-maxLength-valid: Value '42' with length = '2' is not facet-valid with respect to " +
                        "maxLength '1' for type '#AnonType_KodeOpphevelseType'."
            ),

            /** Presisering */
            row(
                "empty Presisering",
                "<Opphevelse Kode=\"1\" Presisering=\"\"/>",
                "cvc-minLength-valid: Value '' with length = '0' is not facet-valid with respect " +
                        "to minLength '1' for type '#AnonType_Presisering'."
            ),
            row(
                "too long Presisering",
                "<Opphevelse Kode=\"1\" Presisering=\"${"a".repeat(CLARIFICATION_MAX_LEN + 1)}\"/>",
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
        private fun buildXmlInTest(opphevelseXml: String): String = buildBarnevernXml(
            "<Tiltak Id=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" StartDato=\"2022-11-14\">" +
                    LOVHJEMMEL_XML +
                    "<Kategori Kode=\"1.1\" />" +
                    opphevelseXml +
                    "</Tiltak>"
        )
    }
}
