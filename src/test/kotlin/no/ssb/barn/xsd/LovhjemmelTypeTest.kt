package no.ssb.barn.xsd

import io.kotest.assertions.throwables.shouldNotThrowAny
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.data.forAll
import io.kotest.data.row
import io.kotest.matchers.shouldBe
import no.ssb.barn.TestUtils.VALID_DATE
import no.ssb.barn.TestUtils.buildBarnevernXml
import no.ssb.barn.toStreamSource
import no.ssb.barn.util.ValidationUtils.getSchemaValidatorV3
import org.xml.sax.SAXException

class LovhjemmelTypeTest : BehaviorSpec({

    Given("misc Lovhjemmel XML") {

        /** make sure it's possible to make a valid test XML */
        When("valid XML, expect no exceptions") {
            shouldNotThrowAny {
                getSchemaValidatorV3().validate(
                    buildXmlInTest(
                        "<Lovhjemmel>" +
                                "<Lov>BVL</Lov>" +
                                "<Kapittel>1b</Kapittel>" +
                                "<Paragraf>4a</Paragraf>" +
                                "<Ledd>3</Ledd>" +
                                "<Bokstav>a</Bokstav>" +
                                "<Punktum>4</Punktum>" +
                                "</Lovhjemmel>"
                    ).toStreamSource()
                )
            }
        }

        forAll(
            /** Lov */
            row(
                "missing Lov",
                "<Lovhjemmel>" +
                        "<Kapittel>1</Kapittel>" +
                        "<Paragraf>2</Paragraf>" +
                        "</Lovhjemmel>",
                "cvc-complex-type.2.4.a: Invalid content was found starting with element 'Kapittel'. " +
                        "One of '{Lov}' is expected."
            ),
            row(
                "empty Lov",
                "<Lovhjemmel>" +
                        "<Lov></Lov>" +
                        "<Kapittel>1</Kapittel>" +
                        "<Paragraf>2</Paragraf>" +
                        "</Lovhjemmel>",
                "cvc-enumeration-valid: Value '' is not facet-valid with respect to enumeration '[BVL, BVL2021, BL1981]'. " +
                        "It must be a value from the enumeration."
            ),
            row(
                "invalid Lov",
                "<Lovhjemmel>" +
                        "<Lov>BVL2022</Lov>" +
                        "<Kapittel>1</Kapittel>" +
                        "<Paragraf>2</Paragraf>" +
                        "</Lovhjemmel>",
                "cvc-enumeration-valid: Value 'BVL2022' is not facet-valid with respect " +
                        "to enumeration '[BVL, BVL2021, BL1981]'. It must be a value from the enumeration."
            ),

            /** Kapittel */
            row(
                "missing Kapittel",
                "<Lovhjemmel>" +
                        "<Lov>BVL</Lov>" +
                        "<Paragraf>2</Paragraf>" +
                        "</Lovhjemmel>",
                "cvc-complex-type.2.4.a: Invalid content was found starting with element 'Paragraf'. " +
                        "One of '{Kapittel}' is expected."
            ),
            row(
                "empty Kapittel",
                "<Lovhjemmel>" +
                        "<Lov>BVL</Lov>" +
                        "<Kapittel></Kapittel>" +
                        "<Paragraf>2</Paragraf>" +
                        "</Lovhjemmel>",
                "cvc-pattern-valid: Value '' is not facet-valid with respect to pattern '\\d+[a-z]*' for " +
                        "type '#AnonType_KapittelLovhjemmelType'."
            ),
            row(
                "too long Kapittel",
                "<Lovhjemmel>" +
                        "<Lov>BVL</Lov>" +
                        "<Kapittel>1234</Kapittel>" +
                        "<Paragraf>2</Paragraf>" +
                        "</Lovhjemmel>",
                "cvc-maxLength-valid: Value '1234' with length = '4' is not facet-valid with " +
                        "respect to maxLength '3' for type '#AnonType_KapittelLovhjemmelType'."
            ),

            /** Paragraf */
            row(
                "missing Paragraf",
                "<Lovhjemmel>" +
                        "<Lov>BVL</Lov>" +
                        "<Kapittel>1</Kapittel>" +
                        "</Lovhjemmel>",
                "cvc-complex-type.2.4.b: The content of element 'Lovhjemmel' is not complete. " +
                        "One of '{Paragraf}' is expected."
            ),
            row(
                "empty Paragraf",
                "<Lovhjemmel>" +
                        "<Lov>BVL</Lov>" +
                        "<Kapittel>1</Kapittel>" +
                        "<Paragraf></Paragraf>" +
                        "</Lovhjemmel>",
                "cvc-pattern-valid: Value '' is not facet-valid with respect to pattern '\\d+[a-z]*' for type " +
                        "'#AnonType_ParagrafLovhjemmelType'."
            ),
            row(
                "too long Paragraf",
                "<Lovhjemmel>" +
                        "<Lov>BVL</Lov>" +
                        "<Kapittel>1</Kapittel>" +
                        "<Paragraf>12345</Paragraf>" +
                        "</Lovhjemmel>",
                "cvc-maxLength-valid: Value '12345' with length = '5' is not facet-valid with " +
                        "respect to maxLength '4' for type '#AnonType_ParagrafLovhjemmelType'."
            ),
            row(
                "Paragraf with digit, space and letter",
                "<Lovhjemmel>" +
                        "<Lov>BVL</Lov>" +
                        "<Kapittel>1</Kapittel>" +
                        "<Paragraf>4 a</Paragraf>" +
                        "</Lovhjemmel>",
                "cvc-pattern-valid: Value '4 a' is not facet-valid with respect to pattern '\\d+[a-z]*' for " +
                        "type '#AnonType_ParagrafLovhjemmelType'."
            ),

            /** Ledd */
            row(
                "empty Ledd",
                "<Lovhjemmel>" +
                        "<Lov>BVL</Lov>" +
                        "<Kapittel>1</Kapittel>" +
                        "<Paragraf>2</Paragraf>" +
                        "<Ledd></Ledd>" +
                        "</Lovhjemmel>",
                "cvc-pattern-valid: Value '' is not facet-valid with respect to pattern '\\d+' for type " +
                        "'#AnonType_LeddLovhjemmelType'."
            ),
            row(
                "too long Ledd",
                "<Lovhjemmel>" +
                        "<Lov>BVL</Lov>" +
                        "<Kapittel>1</Kapittel>" +
                        "<Paragraf>2</Paragraf>" +
                        "<Ledd>1234</Ledd>" +
                        "</Lovhjemmel>",
                "cvc-maxLength-valid: Value '1234' with length = '4' is not facet-valid with respect to " +
                        "maxLength '3' for type '#AnonType_LeddLovhjemmelType'."
            ),

            /** Bokstav */
            row(
                "empty Bokstav",
                "<Lovhjemmel>" +
                        "<Lov>BVL</Lov>" +
                        "<Kapittel>1</Kapittel>" +
                        "<Paragraf>2</Paragraf>" +
                        "<Bokstav></Bokstav>" +
                        "</Lovhjemmel>",
                "cvc-pattern-valid: Value '' is not facet-valid with respect to pattern '[a-z]' for type " +
                        "'#AnonType_BokstavLovhjemmelType'."
            ),
            row(
                "too long Bokstav",
                "<Lovhjemmel>" +
                        "<Lov>BVL</Lov>" +
                        "<Kapittel>1</Kapittel>" +
                        "<Paragraf>2</Paragraf>" +
                        "<Bokstav>ab</Bokstav>" +
                        "</Lovhjemmel>",
                "cvc-pattern-valid: Value 'ab' is not facet-valid with respect to pattern '[a-z]' for type " +
                        "'#AnonType_BokstavLovhjemmelType'."
            ),

            /** Punktum */
            row(
                "empty Punktum",
                "<Lovhjemmel>" +
                        "<Lov>BVL</Lov>" +
                        "<Kapittel>1</Kapittel>" +
                        "<Paragraf>2</Paragraf>" +
                        "<Punktum></Punktum>" +
                        "</Lovhjemmel>",
                "cvc-pattern-valid: Value '' is not facet-valid with respect to pattern '\\d+' for type " +
                        "'#AnonType_PunktumLovhjemmelType'."
            ),
            row(
                "too long Punktum",
                "<Lovhjemmel>" +
                        "<Lov>BVL</Lov>" +
                        "<Kapittel>1</Kapittel>" +
                        "<Paragraf>2</Paragraf>" +
                        "<Punktum>1234</Punktum>" +
                        "</Lovhjemmel>",
                "cvc-maxLength-valid: Value '1234' with length = '4' is not facet-valid with respect to " +
                        "maxLength '3' for type '#AnonType_PunktumLovhjemmelType'."
            )
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
        private fun buildXmlInTest(lovhjemmelXml: String): String = buildBarnevernXml(
            "<Tiltak Id=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" StartDato=\"$VALID_DATE\">" +
                    "${lovhjemmelXml}<Kategori Kode=\"1.1\"/></Tiltak>"
        )
    }
}
