package no.ssb.barn.xsd

import io.kotest.assertions.throwables.shouldNotThrowAny
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.data.forAll
import io.kotest.data.row
import io.kotest.matchers.shouldBe
import no.ssb.barn.TestUtils.EMPTY_DATE_ERROR
import no.ssb.barn.TestUtils.EMPTY_ID_ERROR
import no.ssb.barn.TestUtils.END_DATE_TOO_EARLY_ERROR
import no.ssb.barn.TestUtils.END_DATE_TOO_LATE_ERROR
import no.ssb.barn.TestUtils.INVALID_DATE_FORMAT_ERROR
import no.ssb.barn.TestUtils.INVALID_ID_ERROR
import no.ssb.barn.TestUtils.buildBarnevernXml
import no.ssb.barn.toStreamSource
import no.ssb.barn.util.ValidationUtils.getSchemaValidator
import org.xml.sax.SAXException

class SlettetTypeTest : BehaviorSpec({

    Given("misc Slettet XML") {

        /** make sure it's possible to make a valid test XML */
        When("valid XML, expect no exceptions") {
            shouldNotThrowAny {
                getSchemaValidator().validate(
                    buildBarnevernXml(
                        "<Slettet Id=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" " +
                                "Type=\"Melding\" " +
                                "SluttDato=\"2022-11-14\" />"
                    ).toStreamSource()
                )
            }
        }

        forAll(
            /** Id */
            row(
                "duplicate Id",
                "<Slettet Id=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" Type=\"Melding\" SluttDato=\"2022-11-14\" />" +
                        "<Slettet Id=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" Type=\"Melding\" SluttDato=\"2022-11-14\" />",
                "cvc-identity-constraint.4.1: Duplicate unique value [6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e,Melding] " +
                        "declared for identity constraint \"SlettetIdUnique\" of element \"Sak\"."
            ),
            row(
                "missing Id",
                "<Slettet Type=\"Melding\" SluttDato=\"2022-11-14\" />",
                "cvc-complex-type.4: Attribute 'Id' must appear on element 'Slettet'."
            ),
            row(
                "empty Id",
                "<Slettet Id=\"\" Type=\"Melding\" SluttDato=\"2022-11-14\" />",
                EMPTY_ID_ERROR
            ),
            row(
                "invalid Id",
                "<Slettet Id=\"42\" Type=\"Melding\" SluttDato=\"2022-11-14\" />",
                INVALID_ID_ERROR
            ),

            /** Type */
            row(
                "missing Type",
                "<Slettet Id=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" SluttDato=\"2022-11-14\" />",
                "cvc-complex-type.4: Attribute 'Type' must appear on element 'Slettet'."
            ),
            row(
                "empty Type",
                "<Slettet Id=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" Type=\"\" SluttDato=\"2022-11-14\" />",
                "cvc-enumeration-valid: Value '' is not facet-valid with respect to enumeration " +
                        "'[Personalia, Melding, Undersokelse, Plan, Tiltak, Vedtak, Status, Ettervern, " +
                        "OversendelseFylkesnemnd, Flytting, Relasjon]'. It must be a value from the enumeration."
            ),
            row(
                "invalid Type",
                "<Slettet Id=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" Type=\"42\" SluttDato=\"2022-11-14\" />",
                "cvc-enumeration-valid: Value '42' is not facet-valid with respect to enumeration " +
                        "'[Personalia, Melding, Undersokelse, Plan, Tiltak, Vedtak, Status, Ettervern, " +
                        "OversendelseFylkesnemnd, Flytting, Relasjon]'. It must be a value from the enumeration."
            ),

            /** SluttDato */
            row(
                "missing SluttDato",
                "<Slettet Id=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" Type=\"Melding\" />",
                "cvc-complex-type.4: Attribute 'SluttDato' must appear on element 'Slettet'."
            ),
            row(
                "empty SluttDato",
                "<Slettet Id=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" Type=\"Melding\" SluttDato=\"\" />",
                EMPTY_DATE_ERROR
            ),
            row(
                "invalid SluttDato",
                "<Slettet Id=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" Type=\"Melding\" SluttDato=\"2022\" />",
                INVALID_DATE_FORMAT_ERROR
            ),
            row(
                "SluttDato too early",
                "<Slettet Id=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" Type=\"Melding\" SluttDato=\"1997-12-31\" />",
                END_DATE_TOO_EARLY_ERROR
            ),
            row(
                "SluttDato too late",
                "<Slettet Id=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" Type=\"Melding\" SluttDato=\"2030-01-01\" />",
                END_DATE_TOO_LATE_ERROR
            )
        ) { description, fagsystemXml, expectedError ->
            When(description) {
                val thrown = shouldThrow<SAXException> {
                    getSchemaValidator().validate(buildBarnevernXml(fagsystemXml).toStreamSource())
                }

                Then("thrown should be as expected") {
                    thrown.message shouldBe expectedError
                }
            }
        }
    }
})