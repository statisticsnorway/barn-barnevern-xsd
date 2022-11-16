package no.ssb.barn.xsd

import io.kotest.assertions.throwables.shouldNotThrowAny
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.data.forAll
import io.kotest.data.row
import io.kotest.matchers.shouldBe
import no.ssb.barn.TestUtils.buildBarnevernXml
import no.ssb.barn.toStreamSource
import no.ssb.barn.util.ValidationUtils.getSchemaValidator
import org.xml.sax.SAXException

class TilsynHyppighetTypeTest : BehaviorSpec({

    given("misc TilsynHyppighetType XML") {

        /** make sure it's possible to make a valid test XML */
        `when`("valid XML, expected no exceptions") {
            shouldNotThrowAny {
                getSchemaValidator().validate(
                    buildXmlInTest(
                        "<Hyppighet Id=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" " +
                                "StartDato=\"2022-11-14\" Kode=\"2\"/>"
                    ).toStreamSource()
                )
            }
        }

        forAll(
            /** Id */
            row(
                "missing Id",
                "<Hyppighet StartDato=\"2022-11-14\" Kode=\"2\"/>",
                "cvc-complex-type.4: Attribute 'Id' must appear on element 'Hyppighet'."
            ),
            row(
                "empty Id",
                "<Hyppighet Id=\"\" StartDato=\"2022-11-14\" Kode=\"2\"/>",
                "cvc-pattern-valid: Value '' is not facet-valid with respect to pattern " +
                        "'[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[1-5][0-9a-fA-F]{3}-[89abAB][0-9a-fA-F]{3}-[0-9a-fA-F]{12}' " +
                        "for type '#AnonType_Id'."
            ),
            row(
                "invalid Id",
                "<Hyppighet Id=\"42\" StartDato=\"2022-11-14\" Kode=\"2\"/>",
                "cvc-pattern-valid: Value '42' is not facet-valid with respect to pattern " +
                        "'[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[1-5][0-9a-fA-F]{3}-[89abAB][0-9a-fA-F]{3}-[0-9a-fA-F]{12}' " +
                        "for type '#AnonType_Id'."
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
                "<Hyppighet Id=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" StartDato=\"2022\" Kode=\"2\"/>",
                "cvc-datatype-valid.1.2.1: '2022' is not a valid value for 'date'."
            ),

            /** Kode */
            row(
                "missing Kode",
                "<Hyppighet Id=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" StartDato=\"2022-11-14\" />",
                "cvc-complex-type.4: Attribute 'Kode' must appear on element 'Hyppighet'."
            ),
            row(
                "empty Kode",
                "<Hyppighet Id=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" StartDato=\"2022-11-14\" Kode=\"\"/>",
                "cvc-minLength-valid: Value '' with length = '0' is not facet-valid with respect to " +
                        "minLength '1' for type '#AnonType_KodeTilsynHyppighetType'."
            ),
            row(
                "too long Kode",
                "<Hyppighet Id=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" StartDato=\"2022-11-14\" Kode=\"42\"/>",
                "cvc-maxLength-valid: Value '42' with length = '2' is not facet-valid with respect to " +
                        "maxLength '1' for type '#AnonType_KodeTilsynHyppighetType'."
            )
        ) { description, partialXml, expectedError ->
            `when`(description) {
                val thrown = shouldThrow<SAXException> {
                    getSchemaValidator().validate(buildXmlInTest(partialXml).toStreamSource())
                }

                then("thrown should be as expected") {
                    thrown.message shouldBe expectedError
                }
            }
        }
    }
}) {
    companion object {
        private fun buildXmlInTest(tilsynHyppighetXml: String): String = buildBarnevernXml(
            "<Tiltak Id=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" StartDato=\"2022-11-14\">" +
                    "<Lovhjemmel><Lov>BVL</Lov><Kapittel>1</Kapittel><Paragraf>2</Paragraf></Lovhjemmel>" +
                    "<Kategori Kode=\"1.1\" /><Tilsyn>" +
                    "<Ansvarlig Id=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" " +
                    "StartDato=\"2022-11-14\" Kommunenummer=\"1234\"/>" +
                    tilsynHyppighetXml +
                    "</Tilsyn></Tiltak>"
        )
    }
}
