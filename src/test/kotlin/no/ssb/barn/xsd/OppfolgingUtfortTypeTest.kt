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
import no.ssb.barn.util.ValidationUtils
import org.xml.sax.SAXException

class OppfolgingUtfortTypeTest : BehaviorSpec({

    Given("misc Utfort XML") {

        /** make sure it's possible to make a valid test XML */
        When("valid XML, expect no exceptions") {
            shouldNotThrowAny {
                ValidationUtils.getSchemaValidatorV3().validate(
                    buildXmlInTest(
                        "<Utfort Id=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2f\" " +
                                "UtfortDato=\"$VALID_DATE\"/>"
                    ).toStreamSource()
                )
            }
        }

        forAll(
            /** Id */
            row(
                "missing Id",
                "<Utfort UtfortDato=\"${VALID_DATE}\"/>",
                "cvc-complex-type.4: Attribute 'Id' must appear on element 'Utfort'."
            ),
            row(
                "empty Id",
                "<Utfort Id=\"\" UtfortDato=\"$VALID_DATE\"/>",
                EMPTY_ID_ERROR
            ),
            row(
                "invalid Id",
                "<Utfort Id=\"42\" UtfortDato=\"$VALID_DATE\"/>",
                INVALID_ID_ERROR
            ),

            /** UtfortDato */
            row(
                "missing UtfortDato",
                "<Utfort Id=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2f\"/>",
                "cvc-complex-type.4: Attribute 'UtfortDato' must appear on element 'Utfort'."
            ),
            row(
                "empty UtfortDato",
                "<Utfort Id=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2f\" UtfortDato=\"\"/>",
                EMPTY_DATE_ERROR
            ),
            row(
                "invalid UtfortDato",
                "<Utfort Id=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2f\" UtfortDato=\"${INVALID_DATE}\"/>",
                INVALID_DATE_FORMAT_ERROR
            ),
        ) { description, partialXml, expectedError ->
            When(description) {
                val thrown = shouldThrow<SAXException> {
                    ValidationUtils.getSchemaValidatorV3().validate(buildXmlInTest(partialXml).toStreamSource())
                }

                Then("thrown should be as expected") {
                    thrown.message shouldBe expectedError
                }
            }
        }
    }
}) {
    companion object {
        private fun buildXmlInTest(utfortXml: String): String = buildBarnevernXml(
            "<Tiltak Id=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" StartDato=\"$VALID_DATE\">" +
                    LOVHJEMMEL_XML +
                    "<Kategori Kode=\"1.1\" />" +
                    "<Oppfolging>" +
                    "<Hyppighet Id=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" " +
                    "StartDato=\"$VALID_DATE\" Kode=\"2\"/>" +
                    utfortXml +
                    "</Oppfolging>" +
                    "</Tiltak>"
        )
    }
}
