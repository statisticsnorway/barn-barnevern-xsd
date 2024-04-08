package no.ssb.barn.xsd

import io.kotest.assertions.throwables.shouldNotThrowAny
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.data.forAll
import io.kotest.data.row
import io.kotest.matchers.shouldBe
import no.ssb.barn.TestUtils
import no.ssb.barn.toStreamSource
import no.ssb.barn.util.ValidationUtils
import org.xml.sax.SAXException

class OppfolgingHyppighetTypeTest : BehaviorSpec({

    Given("misc Hyppighet XML") {

        /** make sure it's possible to make a valid test XML */
        When("valid XML, expect no exceptions") {
            shouldNotThrowAny {
                ValidationUtils.getSchemaValidator().validate(
                    buildXmlInTest(
                        "<Hyppighet Id=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" " +
                                "StartDato=\"${TestUtils.VALID_DATE}\" Kode=\"2\"/>" +
                                "<Utfort Id=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2f\" " +
                                "UtfortDato=\"${TestUtils.VALID_DATE}\"/>"
                    ).toStreamSource()
                )
            }
        }

        forAll(
            /** Id */
            row(
                "missing Id",
                "<Hyppighet StartDato=\"${TestUtils.VALID_DATE}\" Kode=\"2\"/>",
                "cvc-complex-type.4: Attribute 'Id' must appear on element 'Hyppighet'."
            ),
            row(
                "empty Id",
                "<Hyppighet Id=\"\" StartDato=\"${TestUtils.VALID_DATE}\" Kode=\"2\"/>",
                TestUtils.EMPTY_ID_ERROR
            ),
            row(
                "invalid Id",
                "<Hyppighet Id=\"42\" StartDato=\"${TestUtils.VALID_DATE}\" Kode=\"2\"/>",
                TestUtils.INVALID_ID_ERROR
            ),

            /** StartDato */
            row(
                "missing StartDato",
                "<Hyppighet Id=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" Kode=\"2\"/>",
                "cvc-complex-type.4: Attribute 'StartDato' must appear on element 'Hyppighet'."
            ),
            row(
                "empty StartDato",
                "<Hyppighet Id=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" StartDato=\"\" Kode=\"2\"/>",
                "cvc-datatype-valid.1.2.1: '' is not a valid value for 'date'."
            ),
            row(
                "invalid StartDato",
                "<Hyppighet Id=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" StartDato=\"INVALID\" Kode=\"2\"/>",
                "cvc-datatype-valid.1.2.1: 'INVALID' is not a valid value for 'date'."
            ),

            /** Kode */
            row(
                "missing Kode",
                "<Hyppighet Id=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" " +
                        "StartDato=\"${TestUtils.VALID_DATE}\"/>",
                "cvc-complex-type.4: Attribute 'Kode' must appear on element 'Hyppighet'."
            ),
            row(
                "empty Kode",
                "<Hyppighet Id=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" " +
                        "StartDato=\"${TestUtils.VALID_DATE}\" Kode=\"\"/>",
                "cvc-minLength-valid: Value '' with length = '0' is not facet-valid with respect to " +
                        "minLength '1' for type '#AnonType_KodeOppfolgingHyppighetType'."
            ),
            row(
                "invalid Kode",
                "<Hyppighet Id=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" " +
                        "StartDato=\"${TestUtils.VALID_DATE}\" Kode=\"X\"/>",
                "cvc-enumeration-valid: Value 'X' is not facet-valid with respect to enumeration '[4, 2]'. " +
                        "It must be a value from the enumeration."
            ),
        ) { description, partialXml, expectedError ->
            When(description) {
                val thrown = shouldThrow<SAXException> {
                    ValidationUtils.getSchemaValidator().validate(buildXmlInTest(partialXml).toStreamSource())
                }

                Then("thrown should be as expected") {
                    thrown.message shouldBe expectedError
                }
            }
        }
    }
}) {
    companion object {
        private fun buildXmlInTest(oppfolgingXml: String): String = TestUtils.buildBarnevernXml(
            "<Tiltak Id=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" StartDato=\"${TestUtils.VALID_DATE}\">" +
                    TestUtils.LOVHJEMMEL_XML +
                    "<Kategori Kode=\"1.1\" />" +
                    "<Oppfolging>" +
                    oppfolgingXml +
                    "</Oppfolging>" +
                    "</Tiltak>"
        )
    }
}
