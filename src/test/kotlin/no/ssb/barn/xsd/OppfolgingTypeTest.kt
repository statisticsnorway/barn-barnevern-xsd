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

class OppfolgingTypeTest : BehaviorSpec({

    given("misc OppfolgingType XML") {

        /** make sure it's possible to make a valid test XML */
        `when`("valid XML, expected no exceptions") {
            shouldNotThrowAny {
                getSchemaValidator().validate(
                    buildXmlInTest(
                        "<Oppfolging Id=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" UtfortDato=\"2022-11-14\"/>"
                    ).toStreamSource()
                )
            }
        }

        forAll(
            /** Id */
            row(
                "missing Id",
                "<Oppfolging UtfortDato=\"2022-11-14\"/>",
                "cvc-complex-type.4: Attribute 'Id' must appear on element 'Oppfolging'."
            ),
            row(
                "empty Id",
                "<Oppfolging Id=\"\" UtfortDato=\"2022-11-14\"/>",
                EMPTY_ID_ERROR
            ),
            row(
                "invalid Id",
                "<Oppfolging Id=\"42\" UtfortDato=\"2022-11-14\"/>",
                INVALID_ID_ERROR
            ),

            /** UtfortDato */
            row(
                "missing UtfortDato",
                "<Oppfolging Id=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" />",
                "cvc-complex-type.4: Attribute 'UtfortDato' must appear on element 'Oppfolging'."
            ),
            row(
                "empty UtfortDato",
                "<Oppfolging Id=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" UtfortDato=\"\"/>",
                EMPTY_DATE_ERROR
            ),
            row(
                "invalid UtfortDato",
                "<Oppfolging Id=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" UtfortDato=\"2022\"/>",
                INVALID_DATE_ERROR
            )
        ) { description, partialXml, expectedError ->
            `when`(description) {
                val thrown = shouldThrow<SAXException> {
                    getSchemaValidator().validate(buildXmlInTest(partialXml).toStreamSource())
                }

                then("thrown should be as expected") {
                    thrown.message shouldBe expectedError
                }
            }
        }
    }
}) {
    companion object {
        private fun buildXmlInTest(oppfolgingXml: String): String = buildBarnevernXml(
            "<Tiltak Id=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" StartDato=\"2022-11-14\">" +
                    LOVHJEMMEL_XML +
                    "<Kategori Kode=\"1.1\" />" +
                    oppfolgingXml +
                    "</Tiltak>"
        )
    }
}
