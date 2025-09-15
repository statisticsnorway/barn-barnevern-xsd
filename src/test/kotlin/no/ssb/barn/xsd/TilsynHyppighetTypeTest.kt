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
import no.ssb.barn.util.ValidationUtils.getSchemaValidatorV4
import org.xml.sax.SAXException

class TilsynHyppighetTypeTest : BehaviorSpec({

    Given("misc TilsynHyppighet XML") {

        /** make sure it's possible to make a valid test XML */
        When("valid XML, expect no exceptions") {
            shouldNotThrowAny {
                getSchemaValidatorV4().validate(
                    buildXmlInTest(
                        "<Hyppighet Id=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" " +
                                "StartDato=\"$VALID_DATE\" Kode=\"2\"/>"
                    ).toStreamSource()
                )
            }
        }

        forAll(
            /** Id */
            row(
                "missing Id",
                "<Hyppighet StartDato=\"$VALID_DATE\" Kode=\"2\"/>",
                "cvc-complex-type.4: Attribute 'Id' must appear on element 'Hyppighet'."
            ),
            row(
                "empty Id",
                "<Hyppighet Id=\"\" StartDato=\"$VALID_DATE\" Kode=\"2\"/>",
                EMPTY_ID_ERROR
            ),
            row(
                "invalid Id",
                "<Hyppighet Id=\"42\" StartDato=\"$VALID_DATE\" Kode=\"2\"/>",
                INVALID_ID_ERROR
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
                EMPTY_DATE_ERROR
            ),
            row(
                "invalid StartDato",
                "<Hyppighet Id=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" StartDato=\"$INVALID_DATE\" Kode=\"2\"/>",
                INVALID_DATE_FORMAT_ERROR
            ),

            /** Kode */
            row(
                "missing Kode",
                "<Hyppighet Id=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" StartDato=\"$VALID_DATE\" />",
                "cvc-complex-type.4: Attribute 'Kode' must appear on element 'Hyppighet'."
            ),
            row(
                "empty Kode",
                "<Hyppighet Id=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" StartDato=\"$VALID_DATE\" Kode=\"\"/>",
                "cvc-minLength-valid: Value '' with length = '0' is not facet-valid with respect to " +
                        "minLength '1' for type '#AnonType_KodeTilsynHyppighetType'."
            ),
            row(
                "too long Kode",
                "<Hyppighet Id=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" StartDato=\"$VALID_DATE\" Kode=\"42\"/>",
                "cvc-maxLength-valid: Value '42' with length = '2' is not facet-valid with respect to " +
                        "maxLength '1' for type '#AnonType_KodeTilsynHyppighetType'."
            )
        ) { description, partialXml, expectedError ->
            When(description) {
                val thrown = shouldThrow<SAXException> {
                    getSchemaValidatorV4().validate(buildXmlInTest(partialXml).toStreamSource())
                }

                Then("thrown should be as expected") {
                    thrown.message shouldBe expectedError
                }
            }
        }
    }
}) {
    companion object {
        private fun buildXmlInTest(tilsynHyppighetXml: String): String = buildBarnevernXml(
            "<Tiltak Id=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" StartDato=\"$VALID_DATE\">" +
                    LOVHJEMMEL_XML +
                    "<Kategori Kode=\"1.1\" /><Tilsyn>" +
                    "<Ansvarlig Id=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" " +
                    "StartDato=\"$VALID_DATE\" Kommunenummer=\"1234\"/>" +
                    tilsynHyppighetXml +
                    "</Tilsyn></Tiltak>"
        )
    }
}
