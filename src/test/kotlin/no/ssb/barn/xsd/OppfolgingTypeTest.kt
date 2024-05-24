package no.ssb.barn.xsd

import io.kotest.assertions.throwables.shouldNotThrowAny
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.data.forAll
import io.kotest.data.row
import io.kotest.matchers.shouldBe
import no.ssb.barn.TestUtils.LOVHJEMMEL_XML
import no.ssb.barn.TestUtils.VALID_DATE
import no.ssb.barn.TestUtils.buildBarnevernXml
import no.ssb.barn.toStreamSource
import no.ssb.barn.util.ValidationUtils.getSchemaValidatorV3
import org.xml.sax.SAXException

class OppfolgingTypeTest : BehaviorSpec({

    Given("valid XML, expect no exceptions") {
        forAll(
            row(
                "Full XML",

                "<Hyppighet Id=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" " +
                        "StartDato=\"$VALID_DATE\" Kode=\"2\"/>" +
                        "<Utfort Id=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2f\" " +
                        "UtfortDato=\"$VALID_DATE\"/>"
            ),
            row(
                "Hyppighet only",
                "<Hyppighet Id=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" " +
                        "StartDato=\"$VALID_DATE\" Kode=\"2\"/>"
            )
        ) { description, partialXml ->
            /** make sure it's possible to make a valid test XML */
            When(description) {
                shouldNotThrowAny {
                    getSchemaValidatorV3().validate(
                        buildXmlInTest(partialXml).toStreamSource()
                    )
                }
            }
        }

        forAll(
            row(
                "Missing Hyppighet",
                "<Utfort Id=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2f\" UtfortDato=\"$VALID_DATE\"/>",
                "cvc-complex-type.2.4.a: Invalid content was found starting with element 'Utfort'. " +
                        "One of '{Hyppighet}' is expected."
            ),
        ) { description, partialXml, expectedError ->
            When(description) {
                val thrown = shouldThrow<SAXException> {
                    getSchemaValidatorV3().validate(buildXmlInTest(partialXml).toStreamSource())
                }

                Then("thrown should be as expected") {
                    thrown.message shouldBe expectedError
                }
            }
        }
    }
}) {
    companion object {
        private fun buildXmlInTest(oppfolgingXml: String): String = buildBarnevernXml(
            "<Tiltak Id=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" StartDato=\"$VALID_DATE\">" +
                    LOVHJEMMEL_XML +
                    "<Kategori Kode=\"1.1\" />" +
                    "<Oppfolging>" +
                    oppfolgingXml +
                    "</Oppfolging>" +
                    "</Tiltak>"
        )
    }
}
