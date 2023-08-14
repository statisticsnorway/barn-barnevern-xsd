package no.ssb.barn.xsd

import io.kotest.assertions.throwables.shouldNotThrowAny
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.data.forAll
import io.kotest.data.row
import io.kotest.matchers.shouldBe
import no.ssb.barn.TestUtils.CLARIFICATION_MAX_LEN
import no.ssb.barn.TestUtils.EMPTY_DATE_ERROR
import no.ssb.barn.TestUtils.END_DATE_TOO_EARLY_ERROR
import no.ssb.barn.TestUtils.END_DATE_TOO_LATE_ERROR
import no.ssb.barn.TestUtils.INVALID_CLARIFICATION_ERROR
import no.ssb.barn.TestUtils.INVALID_DATE_FORMAT_ERROR
import no.ssb.barn.TestUtils.buildBarnevernXml
import no.ssb.barn.toStreamSource
import no.ssb.barn.util.ValidationUtils.getSchemaValidator
import org.xml.sax.SAXException

class UndersokelseKonklusjonTypeTest : BehaviorSpec({

    Given("misc UndersokelseKonklusjon XML") {

        /** make sure it's possible to make a valid test XML */
        When("valid XML, expect no exceptions") {
            shouldNotThrowAny {
                getSchemaValidator().validate(
                    buildUndersokelseXml(
                        "<Konklusjon SluttDato=\"2022-11-14\" Kode=\"1\" " +
                                "Presisering=\"~Presisering~\" />"
                    ).toStreamSource()
                )
            }
        }

        forAll(
            /** SluttDato */
            row(
                "missing SluttDato",
                "<Konklusjon  Kode=\"1\" />",
                "cvc-complex-type.4: Attribute 'SluttDato' must appear on element 'Konklusjon'."
            ),
            row(
                "empty SluttDato",
                "<Konklusjon SluttDato=\"\" Kode=\"1\" />",
                EMPTY_DATE_ERROR
            ),
            row(
                "invalid SluttDato",
                "<Konklusjon SluttDato=\"2022\" Kode=\"1\" />",
                INVALID_DATE_FORMAT_ERROR
            ),
            row(
                "SluttDato too early",
                "<Konklusjon SluttDato=\"1997-12-31\" Kode=\"1\" />",
                END_DATE_TOO_EARLY_ERROR
            ),
            row(
                "SluttDato too late",
                "<Konklusjon SluttDato=\"2030-01-01\" Kode=\"1\" />",
                END_DATE_TOO_LATE_ERROR
            ),

            /** Kode */
            row(
                "missing Kode",
                "<Konklusjon SluttDato=\"2022-11-14\" />",
                "cvc-complex-type.4: Attribute 'Kode' must appear on element 'Konklusjon'."
            ),
            row(
                "empty Kode",
                "<Konklusjon SluttDato=\"2022-11-14\" Kode=\"\" />",
                "cvc-minLength-valid: Value '' with length = '0' is not facet-valid with respect to " +
                        "minLength '1' for type '#AnonType_KodeKonklusjonUndersokelseType'."
            ),
            row(
                "invalid Kode",
                "<Konklusjon SluttDato=\"2022-11-14\" Kode=\"42\" />",
                "cvc-maxLength-valid: Value '42' with length = '2' is not facet-valid with respect to " +
                        "maxLength '1' for type '#AnonType_KodeKonklusjonUndersokelseType'."
            ),

            /** Presisering */
            row(
                "empty Presisering",
                "<Konklusjon SluttDato=\"2022-11-14\" Kode=\"1\" Presisering=\"\" />",
                "cvc-minLength-valid: Value '' with length = '0' is not facet-valid with respect to " +
                        "minLength '1' for type '#AnonType_Presisering'."
            ),
            row(
                "too long Presisering",
                "<Konklusjon SluttDato=\"2022-11-14\" Kode=\"1\" Presisering=\"${"a".repeat(CLARIFICATION_MAX_LEN + 1)}\" />",
                INVALID_CLARIFICATION_ERROR
            )
        ) { description, partialXml, expectedError ->
            When(description) {
                val thrown = shouldThrow<SAXException> {
                    getSchemaValidator().validate(buildUndersokelseXml(partialXml).toStreamSource())
                }

                Then("thrown should be as expected") {
                    thrown.message shouldBe expectedError
                }
            }
        }
    }
}) {
    companion object {
        private fun buildUndersokelseXml(undersokelseKonklusjonXml: String): String = buildBarnevernXml(
            "<Undersokelse Id=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" StartDato=\"2022-11-14\">" +
                    undersokelseKonklusjonXml +
                    "</Undersokelse>"
        )
    }
}