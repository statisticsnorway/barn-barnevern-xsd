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
import no.ssb.barn.TestUtils.LOVHJEMMEL_XML
import no.ssb.barn.TestUtils.VALID_DATE
import no.ssb.barn.TestUtils.buildBarnevernXml
import no.ssb.barn.toStreamSource
import no.ssb.barn.util.ValidationUtils.getSchemaValidatorV3
import org.xml.sax.SAXException

class VedtakStatusTypeTest : BehaviorSpec({

    Given("misc VedtakStatus XML") {

        /** make sure it's possible to make a valid test XML */
        When("valid XML, expect no exceptions") {
            shouldNotThrowAny {
                getSchemaValidatorV3().validate(
                    buildVedtakXml(
                        "<Status Id=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" " +
                                "EndretDato=\"$VALID_DATE\" Kode=\"1\" />"
                    ).toStreamSource()
                )
            }
        }

        forAll(
            /** Id */
            row(
                "missing Id",
                "<Status EndretDato=\"$VALID_DATE\" Kode=\"1\" />",
                "cvc-complex-type.4: Attribute 'Id' must appear on element 'Status'."
            ),
            row(
                "empty Id",
                "<Status Id=\"\" EndretDato=\"$VALID_DATE\" Kode=\"1\" />",
                EMPTY_ID_ERROR
            ),
            row(
                "invalid Id",
                "<Status Id=\"42\" EndretDato=\"$VALID_DATE\" Kode=\"1\" />",
                INVALID_ID_ERROR
            ),

            /** EndretDato */
            row(
                "missing EndretDato",
                "<Status Id=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" />",
                "cvc-complex-type.4: Attribute 'EndretDato' must appear on element 'Status'."
            ),
            row(
                "empty EndretDato",
                "<Status Id=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" EndretDato=\"\" Kode=\"1\" />",
                EMPTY_DATE_ERROR
            ),
            row(
                "invalid EndretDato",
                "<Status Id=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" EndretDato=\"$INVALID_DATE\" Kode=\"1\" />",
                INVALID_DATE_FORMAT_ERROR
            ),

            /** Kode */
            row(
                "missing Kode",
                "<Status Id=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" EndretDato=\"$VALID_DATE\" />",
                "cvc-complex-type.4: Attribute 'Kode' must appear on element 'Status'."
            ),
            row(
                "empty Kode",
                "<Status Id=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" EndretDato=\"$VALID_DATE\" Kode=\"\" />",
                "cvc-enumeration-valid: Value '' is not facet-valid with respect to enumeration " +
                        "'[1, 2, 3, 4]'. It must be a value from the enumeration."
            ),
            row(
                "invalid Kode",
                "<Status Id=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" EndretDato=\"$VALID_DATE\" Kode=\"42\" />",
                "cvc-enumeration-valid: Value '42' is not facet-valid with respect to enumeration " +
                        "'[1, 2, 3, 4]'. It must be a value from the enumeration."
            ),
        ) { description, partialXml, expectedError ->
            When(description) {
                val thrown = shouldThrow<SAXException> {
                    getSchemaValidatorV3().validate(buildVedtakXml(partialXml).toStreamSource())
                }

                Then("thrown should be as expected") {
                    thrown.message shouldBe expectedError
                }
            }
        }
    }
}) {
    companion object {
        private fun buildVedtakXml(vedtakStatusXml: String): String = buildBarnevernXml(
            "<Vedtak Id=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" StartDato=\"$VALID_DATE\">" +
                    LOVHJEMMEL_XML + vedtakStatusXml + "</Vedtak>"
        )
    }
}