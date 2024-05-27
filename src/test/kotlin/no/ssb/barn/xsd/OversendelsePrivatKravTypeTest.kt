package no.ssb.barn.xsd

import io.kotest.assertions.throwables.shouldNotThrowAny
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.data.forAll
import io.kotest.data.row
import io.kotest.matchers.shouldBe
import no.ssb.barn.TestUtils.EMPTY_DATE_ERROR
import no.ssb.barn.TestUtils.EMPTY_ID_ERROR
import no.ssb.barn.TestUtils.INVALID_DATE
import no.ssb.barn.TestUtils.INVALID_DATE_FORMAT_ERROR
import no.ssb.barn.TestUtils.INVALID_ID_ERROR
import no.ssb.barn.TestUtils.LOVHJEMMEL_XML
import no.ssb.barn.TestUtils.VALID_DATE
import no.ssb.barn.TestUtils.buildBarnevernXml
import no.ssb.barn.toStreamSource
import no.ssb.barn.util.ValidationUtils.getSchemaValidatorV3
import org.xml.sax.SAXException

class OversendelsePrivatKravTypeTest : BehaviorSpec({

    Given("misc OversendelsePrivatKrav XML") {

        /** make sure it's possible to make a valid test XML */
        When("valid XML, expect no exceptions") {
            shouldNotThrowAny {
                getSchemaValidatorV3().validate(
                    buildOversendelsePrivatKravXml(
                        "<Krav Id=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" StartDato=\"$VALID_DATE\" />"
                    ).toStreamSource()
                )
            }
        }

        forAll(
            /** Id */
            row(
                "missing Id",
                "<Krav StartDato=\"$VALID_DATE\" />",
                "cvc-complex-type.4: Attribute 'Id' must appear on element 'Krav'."
            ),
            row(
                "empty Id",
                "<Krav Id=\"\" StartDato=\"$VALID_DATE\" />",
                EMPTY_ID_ERROR
            ),
            row(
                "invalid Id",
                "<Krav Id=\"42\" StartDato=\"$VALID_DATE\" />",
                INVALID_ID_ERROR
            ),

            /** StartDato */
            row(
                "missing StartDato",
                "<Krav Id=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" />",
                "cvc-complex-type.4: Attribute 'StartDato' must appear on element 'Krav'."
            ),
            row(
                "empty StartDato",
                "<Krav Id=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" StartDato=\"\" />",
                EMPTY_DATE_ERROR
            ),
            row(
                "invalid StartDato",
                "<Krav Id=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" StartDato=\"$INVALID_DATE\" />",
                INVALID_DATE_FORMAT_ERROR
            )
        ) { description, partialXml, expectedError ->
            When(description) {
                val thrown = shouldThrow<SAXException> {
                    getSchemaValidatorV3().validate(buildOversendelsePrivatKravXml(partialXml).toStreamSource())
                }

                Then("thrown should be as expected") {
                    thrown.message shouldBe expectedError
                }
            }
        }
    }
}) {
    companion object {
        private fun buildOversendelsePrivatKravXml(innerXml: String): String = buildBarnevernXml(
            "<Vedtak Id=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" StartDato=\"$VALID_DATE\">" +
                    LOVHJEMMEL_XML +
                    innerXml +
                    "<Status Id=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" " +
                    "EndretDato=\"$VALID_DATE\" Kode=\"1\" /></Vedtak>"
        )
    }
}