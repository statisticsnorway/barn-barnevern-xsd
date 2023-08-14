package no.ssb.barn.xsd

import io.kotest.assertions.throwables.shouldNotThrowAny
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.data.forAll
import io.kotest.data.row
import io.kotest.matchers.shouldBe
import no.ssb.barn.TestUtils.EMPTY_DATE_ERROR
import no.ssb.barn.TestUtils.END_DATE_TOO_EARLY_ERROR
import no.ssb.barn.TestUtils.END_DATE_TOO_LATE_ERROR
import no.ssb.barn.TestUtils.INVALID_DATE
import no.ssb.barn.TestUtils.INVALID_DATE_FORMAT_ERROR
import no.ssb.barn.TestUtils.INVALID_MAX_DATE_TOO_LATE
import no.ssb.barn.TestUtils.INVALID_MIN_DATE_TOO_EARLY
import no.ssb.barn.TestUtils.VALID_DATE
import no.ssb.barn.TestUtils.buildBarnevernXml
import no.ssb.barn.toStreamSource
import no.ssb.barn.util.ValidationUtils.getSchemaValidator
import org.xml.sax.SAXException

class MeldingKonklusjonTypeTest : BehaviorSpec({

    Given("misc MeldingKonklusjon XML") {

        /** make sure it's possible to make a valid test XML */
        When("valid XML, expect no exceptions") {
            shouldNotThrowAny {
                getSchemaValidator().validate(
                    buildMeldingKonklusjonXml(
                        "<Konklusjon SluttDato=\"$VALID_DATE\" Kode=\"1\" />"
                    ).toStreamSource()
                )
            }
        }

        forAll(
            /** SluttDato */
            row(
                "missing SluttDato",
                "<Konklusjon Kode=\"1\" />",
                "cvc-complex-type.4: Attribute 'SluttDato' must appear on element 'Konklusjon'."
            ),
            row(
                "empty SluttDato",
                "<Konklusjon SluttDato=\"\" Kode=\"1\" />",
                EMPTY_DATE_ERROR
            ),
            row(
                "invalid SluttDato",
                "<Konklusjon SluttDato=\"$INVALID_DATE\" Kode=\"1\" />",
                INVALID_DATE_FORMAT_ERROR
            ),
            row(
                "SluttDato too early",
                "<Konklusjon SluttDato=\"$INVALID_MIN_DATE_TOO_EARLY\" Kode=\"1\" />",
                END_DATE_TOO_EARLY_ERROR
            ),
            row(
                "SluttDato too late",
                "<Konklusjon SluttDato=\"$INVALID_MAX_DATE_TOO_LATE\" Kode=\"1\" />",
                END_DATE_TOO_LATE_ERROR
            ),

            /** Kode */
            row(
                "missing Kode",
                "<Konklusjon SluttDato=\"$VALID_DATE\" />",
                "cvc-complex-type.4: Attribute 'Kode' must appear on element 'Konklusjon'."
            ),
            row(
                "empty Kode",
                "<Konklusjon SluttDato=\"$VALID_DATE\" Kode=\"\" />",
                "cvc-minLength-valid: Value '' with length = '0' is not facet-valid with respect " +
                        "to minLength '1' for type '#AnonType_KodeKonklusjonMeldingType'."
            ),
            row(
                "invalid Kode",
                "<Konklusjon SluttDato=\"$VALID_DATE\" Kode=\"42\" />",
                "cvc-enumeration-valid: Value '42' is not facet-valid with respect to enumeration " +
                        "'[1, 2, 3, 4]'. It must be a value from the enumeration."
            )
        ) { description, partialXml, expectedError ->
            When(description) {
                val thrown = shouldThrow<SAXException> {
                    getSchemaValidator().validate(buildMeldingKonklusjonXml(partialXml).toStreamSource())
                }

                Then("thrown should be as expected") {
                    thrown.message shouldBe expectedError
                }
            }
        }
    }
}) {
    companion object {
        private fun buildMeldingKonklusjonXml(meldingKonklusjonXml: String): String = buildBarnevernXml(
            "<Melding Id=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" " +
                    "StartDato=\"$VALID_DATE\">" +
                    meldingKonklusjonXml +
                    "</Melding>"
        )
    }
}