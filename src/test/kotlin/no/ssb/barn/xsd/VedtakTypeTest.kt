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
import no.ssb.barn.util.ValidationUtils.getSchemaValidator
import org.xml.sax.SAXException

class VedtakTypeTest : BehaviorSpec({

    Given("misc Vedtak XML") {

        /** make sure it's possible to make a valid test XML */
        When("valid XML, expect no exceptions") {
            shouldNotThrowAny {
                getSchemaValidator().validate(
                    buildVedtakXml(
                        "<Vedtak Id=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" " +
                                "MigrertId=\"4242\" StartDato=\"$VALID_DATE\">"
                    ).toStreamSource()
                )
            }
        }

        forAll(
            /** Id */
            row(
                "missing Id",
                "<Vedtak StartDato=\"$VALID_DATE\">",
                "cvc-complex-type.4: Attribute 'Id' must appear on element 'Vedtak'."
            ),
            row(
                "empty Id",
                "<Vedtak Id=\"\" StartDato=\"$VALID_DATE\">",
                EMPTY_ID_ERROR
            ),
            row(
                "invalid Id",
                "<Vedtak Id=\"42\" StartDato=\"$VALID_DATE\">",
                INVALID_ID_ERROR
            ),

            /** MigrertId */
            row(
                "empty MigrertId",
                "<Vedtak Id=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" MigrertId=\"\" StartDato=\"$VALID_DATE\">",
                "cvc-minLength-valid: Value '' with length = '0' is not facet-valid with respect to minLength '1' " +
                        "for type '#AnonType_MigrertId'."
            ),
            row(
                "too long MigrertId",
                "<Vedtak Id=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" " +
                        "MigrertId=\"${"a".repeat(37)}\" StartDato=\"$VALID_DATE\">",
                "cvc-maxLength-valid: Value '${"a".repeat(37)}' with length = '37' is not facet-valid with " +
                        "respect to maxLength '36' for type '#AnonType_MigrertId'."
            ),

            /** StartDato */
            row(
                "missing StartDato",
                "<Vedtak Id=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\">",
                "cvc-complex-type.4: Attribute 'StartDato' must appear on element 'Vedtak'."
            ),
            row(
                "empty StartDato",
                "<Vedtak Id=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" StartDato=\"\">",
                EMPTY_DATE_ERROR
            ),
            row(
                "invalid StartDato",
                "<Vedtak Id=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" StartDato=\"$INVALID_DATE\">",
                INVALID_DATE_FORMAT_ERROR
            )
        ) { description, partialXml, expectedError ->
            When(description) {
                val thrown = shouldThrow<SAXException> {
                    getSchemaValidator().validate(buildBarnevernXml(partialXml).toStreamSource())
                }

                Then("thrown should be as expected") {
                    thrown.message shouldBe expectedError
                }
            }
        }

        forAll(
            /** Id */
            row(
                "duplicate Id",
                buildVedtakPartialXml("<Vedtak Id=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" StartDato=\"$VALID_DATE\">")
                        + buildVedtakPartialXml("<Vedtak Id=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" StartDato=\"$VALID_DATE\">"),
                "cvc-identity-constraint.4.1: Duplicate unique value [6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e] declared for identity constraint \"VedtakIdUnique\" of element \"Sak\"."
            )
        ) { description, partialXml, expectedError ->
            When(description) {
                val thrown = shouldThrow<SAXException> {
                    getSchemaValidator().validate(buildBarnevernXml(partialXml).toStreamSource())
                }

                Then("thrown should be as expected") {
                    thrown.message shouldBe expectedError
                }
            }
        }
    }
}) {
    companion object {
        private fun buildVedtakXml(vedtakStartTag: String): String = buildBarnevernXml(
            buildVedtakPartialXml(vedtakStartTag)
        )

        private fun buildVedtakPartialXml(vedtakStartTag: String) = vedtakStartTag +
                LOVHJEMMEL_XML +
                "<Status Id=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" EndretDato=\"$VALID_DATE\" Kode=\"1\" />" +
                "</Vedtak>"
    }
}