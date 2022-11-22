package no.ssb.barn.xsd

import io.kotest.assertions.throwables.shouldNotThrowAny
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.data.forAll
import io.kotest.data.row
import io.kotest.matchers.shouldBe
import no.ssb.barn.TestUtils.EMPTY_ID_ERROR
import no.ssb.barn.TestUtils.INVALID_ID_ERROR
import no.ssb.barn.TestUtils.buildBarnevernXml
import no.ssb.barn.toStreamSource
import no.ssb.barn.util.ValidationUtils.getSchemaValidator
import org.xml.sax.SAXException

class RelasjonTypeTest : BehaviorSpec({

    given("misc RelasjonType XML") {

        /** make sure it's possible to make a valid test XML */
        `when`("valid XML, expect no exceptions") {
            shouldNotThrowAny {
                getSchemaValidator().validate(
                    buildBarnevernXml(
                        "<Relasjon Id=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" " +
                                "FraId=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" " +
                                "FraType=\"Melding\" " +
                                "TilId=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" " +
                                "TilType=\"Undersokelse\" />"
                    ).toStreamSource()
                )
            }
        }

        forAll(
            /** Id */
            row(
                "missing Id",
                "<Relasjon " +
                        "FraId=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" " +
                        "FraType=\"Melding\" " +
                        "TilId=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" " +
                        "TilType=\"Undersokelse\" />",
                "cvc-complex-type.4: Attribute 'Id' must appear on element 'Relasjon'."
            ),
            row(
                "empty Id",
                "<Relasjon Id=\"\" " +
                        "FraId=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" " +
                        "FraType=\"Melding\" " +
                        "TilId=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" " +
                        "TilType=\"Undersokelse\" />",
                EMPTY_ID_ERROR
            ),
            row(
                "invalid Id",
                "<Relasjon Id=\"42\" " +
                        "FraId=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" " +
                        "FraType=\"Melding\" " +
                        "TilId=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" " +
                        "TilType=\"Undersokelse\" />",
                INVALID_ID_ERROR
            ),

            /** FraId */
            row(
                "missing FraId",
                "<Relasjon Id=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" " +
                        "FraType=\"Melding\" " +
                        "TilId=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" " +
                        "TilType=\"Undersokelse\" />",
                "cvc-complex-type.4: Attribute 'FraId' must appear on element 'Relasjon'."
            ),
            row(
                "empty FraId",
                "<Relasjon Id=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" " +
                        "FraId=\"\" " +
                        "FraType=\"Melding\" " +
                        "TilId=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" " +
                        "TilType=\"Undersokelse\" />",
                "cvc-minLength-valid: Value '' with length = '0' is not facet-valid with respect " +
                        "to minLength '1' for type 'IdType'."
            ),
            row(
                "too long FraId",
                "<Relasjon Id=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" " +
                        "FraId=\"${"a".repeat(51)}\" " +
                        "FraType=\"Melding\" " +
                        "TilId=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" " +
                        "TilType=\"Undersokelse\" />",
                "cvc-maxLength-valid: Value '${"a".repeat(51)}' with length = '51' is not " +
                        "facet-valid with respect to maxLength '50' for type 'IdType'."
            ),

            /** FraType */
            row(
                "missing FraType",
                "<Relasjon Id=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" " +
                        "FraId=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" " +
                        "TilId=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" " +
                        "TilType=\"Undersokelse\" />",
                "cvc-complex-type.4: Attribute 'FraType' must appear on element 'Relasjon'."
            ),
            row(
                "empty FraType",
                "<Relasjon Id=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" " +
                        "FraId=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" " +
                        "FraType=\"\" " +
                        "TilId=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" " +
                        "TilType=\"Undersokelse\" />",
                "cvc-enumeration-valid: Value '' is not facet-valid with respect to enumeration '[Personalia, " +
                        "Melding, Undersokelse, Plan, Tiltak, Vedtak, Status, Ettervern, OversendelseFylkesnemnd, " +
                        "Flytting, Relasjon]'. It must be a value from the enumeration."
            ),
            row(
                "invalid FraType",
                "<Relasjon Id=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" " +
                        "FraId=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" " +
                        "FraType=\"42\" " +
                        "TilId=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" " +
                        "TilType=\"Undersokelse\" />",
                "cvc-enumeration-valid: Value '42' is not facet-valid with respect to enumeration '[Personalia, " +
                        "Melding, Undersokelse, Plan, Tiltak, Vedtak, Status, Ettervern, OversendelseFylkesnemnd, " +
                        "Flytting, Relasjon]'. It must be a value from the enumeration."
            ),

            /** TilId */
            row(
                "missing TilId",
                "<Relasjon Id=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" " +
                        "FraId=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" " +
                        "FraType=\"Melding\" " +
                        "TilType=\"Undersokelse\" />",
                "cvc-complex-type.4: Attribute 'TilId' must appear on element 'Relasjon'."
            ),
            row(
                "empty TilId",
                "<Relasjon Id=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" " +
                        "FraId=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" " +
                        "FraType=\"Melding\" " +
                        "TilId=\"\" " +
                        "TilType=\"Undersokelse\" />",
                "cvc-minLength-valid: Value '' with length = '0' is not facet-valid with respect " +
                        "to minLength '1' for type 'IdType'."
            ),
            row(
                "too long TilId",
                "<Relasjon Id=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" " +
                        "FraId=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" " +
                        "FraType=\"Melding\" " +
                        "TilId=\"${"a".repeat(51)}\" " +
                        "TilType=\"Undersokelse\" />",
                "cvc-maxLength-valid: Value '${"a".repeat(51)}' with length = '51' is not " +
                        "facet-valid with respect to maxLength '50' for type 'IdType'."
            ),

            /** TilType */
            row(
                "missing TilType",
                "<Relasjon Id=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" " +
                        "FraId=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" " +
                        "FraType=\"Melding\" " +
                        "TilId=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" />",
                "cvc-complex-type.4: Attribute 'TilType' must appear on element 'Relasjon'."
            ),
            row(
                "empty TilType",
                "<Relasjon Id=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" " +
                        "FraId=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" " +
                        "FraType=\"Melding\" " +
                        "TilId=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" " +
                        "TilType=\"\" />",
                "cvc-enumeration-valid: Value '' is not facet-valid with respect to enumeration '[Personalia, " +
                        "Melding, Undersokelse, Plan, Tiltak, Vedtak, Status, Ettervern, OversendelseFylkesnemnd, " +
                        "Flytting, Relasjon]'. It must be a value from the enumeration."
            ),
            row(
                "invalid TilType",
                "<Relasjon Id=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" " +
                        "FraId=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" " +
                        "FraType=\"Melding\" " +
                        "TilId=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" " +
                        "TilType=\"42\" />",
                "cvc-enumeration-valid: Value '42' is not facet-valid with respect to enumeration '[Personalia, " +
                        "Melding, Undersokelse, Plan, Tiltak, Vedtak, Status, Ettervern, OversendelseFylkesnemnd, " +
                        "Flytting, Relasjon]'. It must be a value from the enumeration."
            ),
        ) { description, fagsystemXml, expectedError ->
            `when`(description) {
                val thrown = shouldThrow<SAXException> {
                    getSchemaValidator().validate(buildBarnevernXml(fagsystemXml).toStreamSource())
                }

                then("thrown should be as expected") {
                    thrown.message shouldBe expectedError
                }
            }
        }
    }
})