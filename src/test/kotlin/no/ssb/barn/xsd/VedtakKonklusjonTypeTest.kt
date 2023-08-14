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
import no.ssb.barn.TestUtils.INVALID_DATE_FORMAT_ERROR
import no.ssb.barn.TestUtils.LOVHJEMMEL_XML
import no.ssb.barn.TestUtils.buildBarnevernXml
import no.ssb.barn.toStreamSource
import no.ssb.barn.util.ValidationUtils.getSchemaValidator
import org.xml.sax.SAXException

class VedtakKonklusjonTypeTest : BehaviorSpec({

    Given("misc VedtakKonklusjon XML") {

        /** make sure it's possible to make a valid test XML */
        When("valid XML, expect no exceptions") {
            shouldNotThrowAny {
                getSchemaValidator().validate(
                    buildVedtakXml(
                        "<Konklusjon SluttDato=\"2022-11-14\" />"
                    ).toStreamSource()
                )
            }
        }

        forAll(
            /** SluttDato */
            row(
                "missing SluttDato",
                "<Konklusjon />",
                "cvc-complex-type.4: Attribute 'SluttDato' must appear on element 'Konklusjon'."
            ),
            row(
                "empty SluttDato",
                "<Konklusjon SluttDato=\"\" />",
                EMPTY_DATE_ERROR
            ),
            row(
                "invalid SluttDato",
                "<Konklusjon SluttDato=\"2022\" />",
                INVALID_DATE_FORMAT_ERROR
            ),
            row(
                "SluttDato too early",
                "<Konklusjon SluttDato=\"1997-12-31\" />",
                END_DATE_TOO_EARLY_ERROR
            ),
            row(
                "SluttDato too late",
                "<Konklusjon SluttDato=\"2030-01-01\" />",
                END_DATE_TOO_LATE_ERROR
            )
        ) { description, partialXml, expectedError ->
            When(description) {
                val thrown = shouldThrow<SAXException> {
                    getSchemaValidator().validate(buildVedtakXml(partialXml).toStreamSource())
                }

                Then("thrown should be as expected") {
                    thrown.message shouldBe expectedError
                }
            }
        }
    }
}) {
    companion object {
        private fun buildVedtakXml(vedtakKonklusjonXml: String): String = buildBarnevernXml(
            "<Vedtak Id=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" StartDato=\"2022-11-14\">" +
                    LOVHJEMMEL_XML +
                    "<Status Id=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" EndretDato=\"2022-11-14\" Kode=\"1\" />" +
                    vedtakKonklusjonXml +
                    "</Vedtak>"
        )
    }
}