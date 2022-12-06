package no.ssb.barn.xsd

import io.kotest.assertions.throwables.shouldNotThrowAny
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.data.forAll
import io.kotest.data.row
import io.kotest.matchers.shouldBe
import no.ssb.barn.TestUtils.EMPTY_DATE_ERROR
import no.ssb.barn.TestUtils.INVALID_DATE_ERROR
import no.ssb.barn.TestUtils.LOVHJEMMEL_XML
import no.ssb.barn.TestUtils.buildBarnevernXml
import no.ssb.barn.toStreamSource
import no.ssb.barn.util.ValidationUtils.getSchemaValidator
import org.xml.sax.SAXException

class OversendelsePrivatKravKonklusjonTypeTest : BehaviorSpec({

    given("misc OversendelsePrivatKravKonklusjon XML") {

        /** make sure it's possible to make a valid test XML */
        `when`("valid XML, expect no exceptions") {
            shouldNotThrowAny {
                getSchemaValidator().validate(
                    buildKonklusjonXml(
                        "<Konklusjon SluttDato=\"2022-11-14\" Kode=\"1\" Presisering=\"~Presisering~\" />"
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
                "<Konklusjon SluttDato=\"2022\" Kode=\"1\" />",
                INVALID_DATE_ERROR
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
                "cvc-length-valid: Value '' with length = '0' is not facet-valid with respect to length '1' " +
                        "for type '#AnonType_KodeKonklusjonOversendelsePrivatKravType'."
            ),
            row(
                "invalid Kode",
                "<Konklusjon SluttDato=\"2022-11-14\" Kode=\"4\" />",
                "cvc-enumeration-valid: Value '4' is not facet-valid with respect to enumeration '[1, 2, 9]'. " +
                        "It must be a value from the enumeration."
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
                "<Konklusjon SluttDato=\"2022-11-14\" Kode=\"1\" Presisering=\"${"a".repeat(301)}\" />",
                "cvc-maxLength-valid: Value '${"a".repeat(301)}' with length = '301' is not facet-valid " +
                        "with respect to maxLength '300' for type '#AnonType_Presisering'."
            ),

            ) { description, partialXml, expectedError ->
            `when`(description) {
                val thrown = shouldThrow<SAXException> {
                    getSchemaValidator().validate(buildKonklusjonXml(partialXml).toStreamSource())
                }

                then("thrown should be as expected") {
                    thrown.message shouldBe expectedError
                }
            }
        }
    }
}) {
    companion object {
        private fun buildKonklusjonXml(innerXml: String): String = buildBarnevernXml(
            "<Vedtak Id=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" StartDato=\"2022-11-14\">" +
                    LOVHJEMMEL_XML +
                    "<Krav Id=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" StartDato=\"2022-11-14\">" +
                    innerXml +
                    "</Krav>" +
                    "<Status Id=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" " +
                    "EndretDato=\"2022-11-14\" Kode=\"1\" /></Vedtak>"
        )
    }
}