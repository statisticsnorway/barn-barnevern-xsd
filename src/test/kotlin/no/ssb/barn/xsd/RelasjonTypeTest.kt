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
import no.ssb.barn.util.ValidationUtils.getSchemaValidatorV3
import org.xml.sax.SAXException

class RelasjonTypeTest : BehaviorSpec({

    Given("misc Relasjon XML") {

        /** make sure it's possible to make a valid test XML */
        When("valid XML, expect no exceptions") {
            shouldNotThrowAny {
                getSchemaValidatorV3().validate(
                    buildBarnevernXml(
                        "<Relasjon Id=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" " +
                                "FraId=\"~fraId~\" " +
                                "FraType=\"Melding\" " +
                                "TilId=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" " +
                                "TilType=\"Undersokelse\" ErSlettet=\"true\"/>"
                    ).toStreamSource()
                )
            }
        }

        forAll(
            /** Id */
            row(
                "duplicate Id",
                "<Relasjon " +
                        "Id=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" " +
                        "FraId=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" " +
                        "FraType=\"Melding\" " +
                        "TilId=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" " +
                        "TilType=\"Undersokelse\" />" +
                        "<Relasjon " +
                        "Id=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" " +
                        "FraId=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" " +
                        "FraType=\"Melding\" " +
                        "TilId=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" " +
                        "TilType=\"Undersokelse\" />",
                "cvc-identity-constraint.4.1: Duplicate unique value [6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e] " +
                        "declared for identity constraint \"RelasjonIdUnique\" of element \"Sak\"."
            ),
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
                        "Melding, Undersokelse, Plan, Tiltak, Vedtak, Ettervern, OversendelseFylkesnemnd, " +
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
                        "Melding, Undersokelse, Plan, Tiltak, Vedtak, Ettervern, OversendelseFylkesnemnd, " +
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
                "cvc-pattern-valid: Value '' is not facet-valid with respect to pattern " +
                        "'[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[1-5][0-9a-fA-F]{3}-[89abAB][0-9a-fA-F]{3}-[0-9a-fA-F]{12}' " +
                        "for type 'UuidIdType'."
            ),
            row(
                "invalid TilId",
                "<Relasjon Id=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" " +
                        "FraId=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" " +
                        "FraType=\"Melding\" " +
                        "TilId=\"~not-a-uuid\" " +
                        "TilType=\"Undersokelse\" />",
                "cvc-pattern-valid: Value '~not-a-uuid' is not facet-valid with respect to pattern " +
                        "'[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[1-5][0-9a-fA-F]{3}-[89abAB][0-9a-fA-F]{3}-[0-9a-fA-F]{12}' " +
                        "for type 'UuidIdType'."
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
                        "Melding, Undersokelse, Plan, Tiltak, Vedtak, Ettervern, OversendelseFylkesnemnd, " +
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
                        "Melding, Undersokelse, Plan, Tiltak, Vedtak, Ettervern, OversendelseFylkesnemnd, " +
                        "Flytting, Relasjon]'. It must be a value from the enumeration."
            ),
        ) { description, fagsystemXml, expectedError ->
            When(description) {
                val thrown = shouldThrow<SAXException> {
                    getSchemaValidatorV3().validate(buildBarnevernXml(fagsystemXml).toStreamSource())
                }

                Then("thrown should be as expected") {
                    thrown.message shouldBe expectedError
                }
            }
        }
    }
})