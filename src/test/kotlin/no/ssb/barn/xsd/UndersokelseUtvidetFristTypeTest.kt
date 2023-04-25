package no.ssb.barn.xsd

import io.kotest.assertions.throwables.shouldNotThrowAny
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.data.forAll
import io.kotest.data.row
import io.kotest.matchers.shouldBe
import no.ssb.barn.TestUtils.EMPTY_DATE_ERROR
import no.ssb.barn.TestUtils.INVALID_DATE_ERROR
import no.ssb.barn.TestUtils.buildBarnevernXml
import no.ssb.barn.toStreamSource
import no.ssb.barn.util.ValidationUtils.getSchemaValidator
import org.xml.sax.SAXException

class UndersokelseUtvidetFristTypeTest : BehaviorSpec({

    Given("misc UndersokelseUtvidetFrist XML") {

        /** make sure it's possible to make a valid test XML */
        When("valid XML, expect no exceptions") {
            shouldNotThrowAny {
                getSchemaValidator().validate(
                    buildUndersokelseXml(
                        "<UtvidetFrist StartDato=\"2022-11-14\" Innvilget=\"1\" />"
                    ).toStreamSource()
                )
            }
        }

        forAll(
            /** StartDato */
            row(
                "missing StartDato",
                "<UtvidetFrist Innvilget=\"1\" />",
                "cvc-complex-type.4: Attribute 'StartDato' must appear on element 'UtvidetFrist'."
            ),
            row(
                "empty StartDato",
                "<UtvidetFrist StartDato=\"\" Innvilget=\"1\" />",
                EMPTY_DATE_ERROR
            ),
            row(
                "invalid StartDato",
                "<UtvidetFrist StartDato=\"2022\" Innvilget=\"1\" />",
                INVALID_DATE_ERROR
            ),

            /** Innvilget */
            row(
                "empty Innvilget",
                "<UtvidetFrist StartDato=\"2022-11-14\" Innvilget=\"\" />",
                "cvc-length-valid: Value '' with length = '0' is not facet-valid with respect to length '1' " +
                        "for type '#AnonType_InnvilgetUtvidetFristUndersokelseType'."
            ),
            row(
                "too long Innvilget",
                "<UtvidetFrist StartDato=\"2022-11-14\" Innvilget=\"42\" />",
                "cvc-length-valid: Value '42' with length = '2' is not facet-valid with respect to length '1' " +
                        "for type '#AnonType_InnvilgetUtvidetFristUndersokelseType'."
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
        private fun buildUndersokelseXml(utvidetFristXml: String): String = buildBarnevernXml(
            "<Undersokelse Id=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" StartDato=\"2022-11-14\">" +
                    utvidetFristXml +
                    "</Undersokelse>"
        )
    }
}