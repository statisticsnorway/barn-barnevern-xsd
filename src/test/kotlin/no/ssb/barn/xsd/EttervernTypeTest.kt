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
import no.ssb.barn.TestUtils.buildBarnevernXml
import no.ssb.barn.toStreamSource
import no.ssb.barn.util.ValidationUtils.getSchemaValidator
import org.xml.sax.SAXException

class EttervernTypeTest : BehaviorSpec({

    given("misc EttervernType XML") {

        /** make sure it's possible to make a valid test XML */
        `when`("valid XML, expected no exceptions") {
            shouldNotThrowAny {
                getSchemaValidator().validate(
                    buildBarnevernXml(
                        "<Ettervern Id=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" " +
                                "TilbudSendtDato=\"2022-11-14\"/>"
                    ).toStreamSource()
                )
            }
        }

        forAll(
            /** Id */
            row(
                "missing Id",
                "<Ettervern TilbudSendtDato=\"2022-11-14\"/>",
                "cvc-complex-type.4: Attribute 'Id' must appear on element 'Ettervern'."
            ),
            row(
                "empty Id",
                "<Ettervern Id=\"\" TilbudSendtDato=\"2022-11-14\"/>",
                EMPTY_ID_ERROR
            ),
            row(
                "invalid Id",
                "<Ettervern Id=\"42\" TilbudSendtDato=\"2022-11-14\"/>",
                INVALID_ID_ERROR
            ),

            /** TilbudSendtDato */
            row(
                "missing TilbudSendtDato",
                "<Ettervern Id=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" />",
                "cvc-complex-type.4: Attribute 'TilbudSendtDato' must appear on element 'Ettervern'."
            ),
            row(
                "empty TilbudSendtDato",
                "<Ettervern Id=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" TilbudSendtDato=\"\"/>",
                EMPTY_DATE_ERROR
            ),
            row(
                "invalid TilbudSendtDato",
                "<Ettervern Id=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" TilbudSendtDato=\"2022\"/>",
                INVALID_DATE_ERROR
            )
        ) { description, partialXml, expectedError ->
            `when`(description) {
                val thrown = shouldThrow<SAXException> {
                    getSchemaValidator().validate(buildBarnevernXml(partialXml).toStreamSource())
                }

                then("thrown should be as expected") {
                    thrown.message shouldBe expectedError
                }
            }
        }
    }
})