package no.ssb.barn.xsd

import io.kotest.assertions.throwables.shouldNotThrowAny
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.data.forAll
import io.kotest.data.row
import io.kotest.matchers.shouldBe
import no.ssb.barn.TestUtils.CLARIFICATION_MAX_LEN
import no.ssb.barn.TestUtils.EMPTY_DATE_ERROR
import no.ssb.barn.TestUtils.INVALID_CLARIFICATION_ERROR
import no.ssb.barn.TestUtils.INVALID_DATE
import no.ssb.barn.TestUtils.INVALID_DATE_FORMAT_ERROR
import no.ssb.barn.TestUtils.LOVHJEMMEL_XML
import no.ssb.barn.TestUtils.VALID_DATE
import no.ssb.barn.TestUtils.buildBarnevernXml
import no.ssb.barn.toStreamSource
import no.ssb.barn.util.ValidationUtils.getSchemaValidator
import org.xml.sax.SAXException

class OversendelsePrivatKravKonklusjonTypeTest : BehaviorSpec({

    Given("misc OversendelsePrivatKravKonklusjon XML") {

        /** make sure it's possible to make a valid test XML */
        When("valid XML, expect no exceptions") {
            shouldNotThrowAny {
                getSchemaValidator().validate(
                    buildKonklusjonXml(
                        "<Konklusjon SluttDato=\"$VALID_DATE\" Kode=\"1\" Presisering=\"~Presisering~\" />"
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

            /** Kode */
            row(
                "missing Kode",
                "<Konklusjon SluttDato=\"$VALID_DATE\" />",
                "cvc-complex-type.4: Attribute 'Kode' must appear on element 'Konklusjon'."
            ),
            row(
                "empty Kode",
                "<Konklusjon SluttDato=\"$VALID_DATE\" Kode=\"\" />",
                "cvc-length-valid: Value '' with length = '0' is not facet-valid with respect to length '1' " +
                        "for type '#AnonType_KodeKonklusjonOversendelsePrivatKravType'."
            ),
            row(
                "invalid Kode",
                "<Konklusjon SluttDato=\"$VALID_DATE\" Kode=\"4\" />",
                "cvc-enumeration-valid: Value '4' is not facet-valid with respect to enumeration '[1, 2, 9]'. " +
                        "It must be a value from the enumeration."
            ),

            /** Presisering */
            row(
                "empty Presisering",
                "<Konklusjon SluttDato=\"$VALID_DATE\" Kode=\"1\" Presisering=\"\" />",
                "cvc-minLength-valid: Value '' with length = '0' is not facet-valid with respect to " +
                        "minLength '1' for type '#AnonType_Presisering'."
            ),
            row(
                "too long Presisering",
                "<Konklusjon SluttDato=\"$VALID_DATE\" Kode=\"1\" Presisering=\"${"a".repeat(CLARIFICATION_MAX_LEN + 1)}\" />",
                INVALID_CLARIFICATION_ERROR
            ),

            ) { description, partialXml, expectedError ->
            When(description) {
                val thrown = shouldThrow<SAXException> {
                    getSchemaValidator().validate(buildKonklusjonXml(partialXml).toStreamSource())
                }

                Then("thrown should be as expected") {
                    thrown.message shouldBe expectedError
                }
            }
        }
    }
}) {
    companion object {
        private fun buildKonklusjonXml(innerXml: String): String = buildBarnevernXml(
            "<Vedtak Id=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" StartDato=\"$VALID_DATE\">" +
                    LOVHJEMMEL_XML +
                    "<Krav Id=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" StartDato=\"$VALID_DATE\">" +
                    innerXml +
                    "</Krav>" +
                    "<Status Id=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" " +
                    "EndretDato=\"$VALID_DATE\" Kode=\"1\" /></Vedtak>"
        )
    }
}