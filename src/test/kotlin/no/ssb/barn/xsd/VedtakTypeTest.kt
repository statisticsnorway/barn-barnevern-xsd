package no.ssb.barn.xsd

import io.kotest.assertions.throwables.shouldNotThrowAny
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.data.forAll
import io.kotest.data.row
import io.kotest.matchers.shouldBe
import no.ssb.barn.TestUtils.EMPTY_DATE_ERROR
import no.ssb.barn.TestUtils.EMPTY_ID_ERROR
import no.ssb.barn.TestUtils.INVALID_DATE_ERROR
import no.ssb.barn.TestUtils.INVALID_ID_ERROR
import no.ssb.barn.TestUtils.LOVHJEMMEL_XML
import no.ssb.barn.TestUtils.buildBarnevernXml
import no.ssb.barn.toStreamSource
import no.ssb.barn.util.ValidationUtils.getSchemaValidator
import org.xml.sax.SAXException

class VedtakTypeTest : BehaviorSpec({

    given("misc VedtakType XML") {

        /** make sure it's possible to make a valid test XML */
        `when`("valid XML, expect no exceptions") {
            shouldNotThrowAny {
                getSchemaValidator().validate(
                    buildVedtakXml(
                        "<Vedtak Id=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" StartDato=\"2022-11-14\">"
                    ).toStreamSource()
                )
            }
        }

        forAll(
            /** Id */
            row(
                "missing Id",
                "<Vedtak StartDato=\"2022-11-14\">",
                "cvc-complex-type.4: Attribute 'Id' must appear on element 'Vedtak'."
            ),
            row(
                "empty Id",
                "<Vedtak Id=\"\" StartDato=\"2022-11-14\">",
                EMPTY_ID_ERROR
            ),
            row(
                "invalid Id",
                "<Vedtak Id=\"42\" StartDato=\"2022-11-14\">",
                INVALID_ID_ERROR
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
                "<Vedtak Id=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" StartDato=\"2022\">",
                INVALID_DATE_ERROR
            ),
        ) { description, partialXml, expectedError ->
            `when`(description) {
                val thrown = shouldThrow<SAXException> {
                    getSchemaValidator().validate(buildVedtakXml(partialXml).toStreamSource())
                }

                then("thrown should be as expected") {
                    thrown.message shouldBe expectedError
                }
            }
        }
    }
}) {
    companion object {
        private fun buildVedtakXml(vedtakStartTag: String): String = buildBarnevernXml(
            vedtakStartTag +
                    LOVHJEMMEL_XML +
                    "<Status Id=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" EndretDato=\"2022-11-14\" Kode=\"1\" />" +
                    "</Vedtak>"
        )
    }
}