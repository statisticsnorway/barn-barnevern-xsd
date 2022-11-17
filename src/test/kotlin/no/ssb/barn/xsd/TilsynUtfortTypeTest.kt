package no.ssb.barn.xsd

import io.kotest.assertions.throwables.shouldNotThrowAny
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.data.forAll
import io.kotest.data.row
import io.kotest.matchers.shouldBe
import no.ssb.barn.TestUtils.LOVHJEMMEL_XML
import no.ssb.barn.TestUtils.buildBarnevernXml
import no.ssb.barn.toStreamSource
import no.ssb.barn.util.ValidationUtils.getSchemaValidator
import org.xml.sax.SAXException

class TilsynUtfortTypeTest : BehaviorSpec({

    given("misc TilsynUtfortType XML") {

        /** make sure it's possible to make a valid test XML */
        `when`("valid XML, expected no exceptions") {
            shouldNotThrowAny {
                getSchemaValidator().validate(
                    buildXmlInTest(
                        "<Utfort Id=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" " +
                                "UtfortDato=\"2022-11-14\" />"
                    ).toStreamSource()
                )
            }
        }

        forAll(
            /** Id */
            row(
                "missing Id",
                "<Utfort UtfortDato=\"2022-11-14\" />",
                "cvc-complex-type.4: Attribute 'Id' must appear on element 'Utfort'."
            ),
            row(
                "empty Id",
                "<Utfort Id=\"\" UtfortDato=\"2022-11-14\" />",
                "cvc-pattern-valid: Value '' is not facet-valid with respect to pattern " +
                        "'[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[1-5][0-9a-fA-F]{3}-[89abAB][0-9a-fA-F]{3}-[0-9a-fA-F]{12}' " +
                        "for type '#AnonType_Id'."
            ),
            row(
                "invalid Id",
                "<Utfort Id=\"42\" UtfortDato=\"2022-11-14\" />",
                "cvc-pattern-valid: Value '42' is not facet-valid with respect to pattern " +
                        "'[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[1-5][0-9a-fA-F]{3}-[89abAB][0-9a-fA-F]{3}-[0-9a-fA-F]{12}' " +
                        "for type '#AnonType_Id'."
            ),

            /** UtfortDato */
            row(
                "missing UtfortDato",
                "<Utfort Id=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" />",
                "cvc-complex-type.4: Attribute 'UtfortDato' must appear on element 'Utfort'."
            ),
            row(
                "empty UtfortDato",
                "<Utfort Id=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" UtfortDato=\"\" />",
                "cvc-datatype-valid.1.2.1: '' is not a valid value for 'date'."
            ),
            row(
                "invalid UtfortDato",
                "<Utfort Id=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" UtfortDato=\"2022\" />",
                "cvc-datatype-valid.1.2.1: '2022' is not a valid value for 'date'."
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
        private fun buildXmlInTest(tilsynUtfortXml: String): String = buildBarnevernXml(
            "<Tiltak Id=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" StartDato=\"2022-11-14\">" +
                    LOVHJEMMEL_XML +
                    "<Kategori Kode=\"1.1\" /><Tilsyn>" +
                    "<Ansvarlig Id=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" " +
                    "StartDato=\"2022-11-14\" Kommunenummer=\"1234\"/>" +
                    "<Hyppighet Id=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" " +
                    "StartDato=\"2022-11-14\" Kode=\"2\"/>" +
                    tilsynUtfortXml +
                    "</Tilsyn></Tiltak>"
        )
    }
}
