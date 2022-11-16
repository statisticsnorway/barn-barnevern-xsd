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

class TilsynAnsvarligTypeTest : BehaviorSpec({
    
    given("misc TilsynAnsvarligType XML") {

        /** make sure it's possible to make a valid test XML */
        `when`("valid XML, expected no exceptions") {
            shouldNotThrowAny {
                getSchemaValidator().validate(
                    buildXmlInTest(
                        "<Ansvarlig Id=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" " +
                                "StartDato=\"2022-11-14\" Kommunenummer=\"1234\"/>"
                    ).toStreamSource()
                )
            }
        }

        forAll(
            /** Id */
            row(
                "missing Id",
                "<Ansvarlig StartDato=\"2022-11-14\" Kommunenummer=\"1234\"/>",
                "cvc-complex-type.4: Attribute 'Id' must appear on element 'Ansvarlig'."
            ),
            row(
                "empty Id",
                "<Ansvarlig Id=\"\" StartDato=\"2022-11-14\" Kommunenummer=\"1234\"/>",
                "cvc-pattern-valid: Value '' is not facet-valid with respect to pattern " +
                        "'[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[1-5][0-9a-fA-F]{3}-[89abAB][0-9a-fA-F]{3}-[0-9a-fA-F]{12}' " +
                        "for type '#AnonType_Id'."
            ),
            row(
                "invalid Id",
                "<Ansvarlig Id=\"42\" StartDato=\"2022-11-14\" Kommunenummer=\"1234\"/>",
                "cvc-pattern-valid: Value '42' is not facet-valid with respect to pattern " +
                        "'[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[1-5][0-9a-fA-F]{3}-[89abAB][0-9a-fA-F]{3}-[0-9a-fA-F]{12}' " +
                        "for type '#AnonType_Id'."
            ),

            /** StartDato */
            row(
                "missing StartDato",
                "<Ansvarlig Id=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" Kommunenummer=\"1234\"/>",
                "cvc-complex-type.4: Attribute 'StartDato' must appear on element 'Ansvarlig'."
            ),
            row(
                "empty StartDato",
                "<Ansvarlig Id=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" StartDato=\"\" Kommunenummer=\"1234\"/>",
                "cvc-datatype-valid.1.2.1: '' is not a valid value for 'date'."
            ),
            row(
                "invalid StartDato",
                "<Ansvarlig Id=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" StartDato=\"2022\" Kommunenummer=\"1234\"/>",
                "cvc-datatype-valid.1.2.1: '2022' is not a valid value for 'date'."
            ),

            /** Kommunenummer */
            row(
                "missing Kommunenummer",
                "<Ansvarlig Id=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" StartDato=\"2022-11-14\"/>",
                "cvc-complex-type.4: Attribute 'Kommunenummer' must appear on element 'Ansvarlig'."
            ),
            row(
                "empty Kommunenummer",
                "<Ansvarlig Id=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" StartDato=\"2022-11-14\" Kommunenummer=\"\"/>",
                "cvc-pattern-valid: Value '' is not facet-valid with respect to pattern '\\d{4}' for " +
                        "type '#AnonType_KommunenummerTilsynAnsvarligType'."
            ),
            row(
                "too short Kommunenummer",
                "<Ansvarlig Id=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" StartDato=\"2022-11-14\" Kommunenummer=\"12\"/>",
                "cvc-pattern-valid: Value '12' is not facet-valid with respect to pattern '\\d{4}' for " +
                        "type '#AnonType_KommunenummerTilsynAnsvarligType'."
            ),
            row(
                "too long Kommunenummer",
                "<Ansvarlig Id=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" StartDato=\"2022-11-14\" Kommunenummer=\"12345\"/>",
                "cvc-pattern-valid: Value '12345' is not facet-valid with respect to pattern '\\d{4}' for " +
                        "type '#AnonType_KommunenummerTilsynAnsvarligType'."
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
        private fun buildXmlInTest(tilsynAnsvarligXml: String): String = buildBarnevernXml(
            "<Tiltak Id=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" StartDato=\"2022-11-14\">" +
                    "<Lovhjemmel><Lov>BVL</Lov><Kapittel>1</Kapittel><Paragraf>2</Paragraf></Lovhjemmel>" +
                    "<Kategori Kode=\"1.1\" /><Tilsyn>" +
                    tilsynAnsvarligXml +
                    "<Hyppighet Id=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" StartDato=\"2022-11-14\" Kode=\"2\"/>" +
                    "</Tilsyn></Tiltak>"
        )
    }
}
