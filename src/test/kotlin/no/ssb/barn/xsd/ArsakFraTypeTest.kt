package no.ssb.barn.xsd

import io.kotest.assertions.throwables.shouldNotThrowAny
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.data.forAll
import io.kotest.data.row
import io.kotest.matchers.shouldBe
import no.ssb.barn.TestUtils.buildBarnevernXml
import no.ssb.barn.toStreamSource
import no.ssb.barn.util.ValidationUtils.getSchemaValidator
import org.xml.sax.SAXException

class ArsakFraTypeTest : BehaviorSpec({

    given("misc ArsakFraType XML") {

        /** make sure it's possible to make a valid test XML */
        `when`("valid XML, expected no exceptions") {
            shouldNotThrowAny {
                getSchemaValidator().validate(
                    buildArsakFraXml(
                        "<ArsakFra Kode=\"1.1.1\" Presisering=\"~Presisering~\" />"
                    ).toStreamSource()
                )
            }
        }

        forAll(
            /** Kode */
            row(
                "missing Kode",
                "<ArsakFra />",
                "cvc-complex-type.4: Attribute 'Kode' must appear on element 'ArsakFra'."
            ),
            row(
                "empty Kode",
                "<ArsakFra Kode=\"\" />",
                "cvc-minLength-valid: Value '' with length = '0' is not facet-valid with respect to " +
                        "minLength '3' for type '#AnonType_KodeArsakFraType'."
            ),
            row(
                "invalid Kode",
                "<ArsakFra Kode=\"4242\" />",
                "cvc-enumeration-valid: Value '4242' is not facet-valid with respect to enumeration " +
                        "'[1.1.1, 1.1.2, 1.1.3, 1.1.4, 1.1.5, 1.2.1, 1.2.2, 1.2.3, 1.3, 2.1, 2.2, 2.3, 2.4, 2.5, " +
                        "2.6, 2.7, 2.8, 2.9]'. It must be a value from the enumeration."
            ),

            /** Presisering */
            row(
                "empty Presisering",
                "<ArsakFra Kode=\"1.1.1\" Presisering=\"\" />",
                "cvc-minLength-valid: Value '' with length = '0' is not facet-valid with respect to " +
                        "minLength '1' for type '#AnonType_Presisering'."
            ),
            row(
                "too long Presisering",
                "<ArsakFra Kode=\"1.1.1\" Presisering=\"${"a".repeat(301)}\" />",
                "cvc-maxLength-valid: Value '${"a".repeat(301)}' with length = '301' is not facet-valid " +
                        "with respect to maxLength '300' for type '#AnonType_Presisering'."
            )
        ) { description, partialXml, expectedError ->
            `when`(description) {
                val thrown = shouldThrow<SAXException> {
                    getSchemaValidator().validate(buildArsakFraXml(partialXml).toStreamSource())
                }

                then("thrown should be as expected") {
                    thrown.message shouldBe expectedError
                }
            }
        }
    }
}) {
    companion object {
        fun buildArsakFraXml(innerXml: String): String = buildBarnevernXml(
            "<Flytting Id=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" SluttDato=\"2022-11-14\">" +
                    innerXml +
                    "<FlyttingTil Kode=\"1\" />" +
                    "</Flytting>"
        )
    }
}