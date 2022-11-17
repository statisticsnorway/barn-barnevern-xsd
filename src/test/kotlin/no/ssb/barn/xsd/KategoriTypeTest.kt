package no.ssb.barn.xsd

import io.kotest.assertions.throwables.shouldNotThrowAny
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.data.forAll
import io.kotest.data.row
import io.kotest.matchers.shouldBe
import no.ssb.barn.TestUtils.LOVHJEMMEL_XML
import no.ssb.barn.TestUtils.buildBarnevernXml
import no.ssb.barn.toStreamSource
import no.ssb.barn.util.ValidationUtils.getSchemaValidator
import org.xml.sax.SAXException

class KategoriTypeTest : BehaviorSpec({

    given("misc KategoriType XML") {

        /** make sure it's possible to make a valid test XML */
        `when`("valid XML, expected no exceptions") {
            shouldNotThrowAny {
                getSchemaValidator().validate(
                    buildXmlInTest(
                        "<Kategori Kode=\"1.1\" Presisering=\"~Presisering~\"/>"
                    ).toStreamSource()
                )
            }
        }

        forAll(
            /** Kode */
            row(
                "missing Kode",
                "<Kategori />",
                "cvc-complex-type.4: Attribute 'Kode' must appear on element 'Kategori'."
            ),
            row(
                "empty Kode",
                "<Melder Kode=\"\" />",
                "cvc-complex-type.2.4.a: Invalid content was found starting with element 'Melder'. " +
                        "One of '{JmfrLovhjemmel, Kategori}' is expected."
            ),
            row(
                "invalid Kode",
                "<Kategori Kode=\"42.42\" />",
                "cvc-enumeration-valid: Value '42.42' is not facet-valid with respect to enumeration " +
                        "'[1.1, 1.2, 1.99, 2.1, 2.2, 2.3, 2.4, 2.5, 2.6, 2.99, 3.1, 3.2, 3.3, 3.4, 3.5, 3.6, " +
                        "3.7, 3.8, 3.9, 3.10, 3.99, 4.1, 4.2, 4.3, 4.4, 4.5, 4.6, 4.7, 4.8, 4.9, 4.99, 5.1, 5.2, " +
                        "5.3, 5.4, 5.99, 6.1, 6.2, 6.3, 6.4, 6.99, 7.1, 7.2, 7.3, 7.99, 8.1, 8.2, 8.3, 8.99]'. " +
                        "It must be a value from the enumeration."
            ),

            /** Presisering */
            row(
                "empty Presisering",
                "<Kategori Kode=\"1.1\" Presisering=\"\"/>",
                "cvc-minLength-valid: Value '' with length = '0' is not facet-valid with respect " +
                        "to minLength '1' for type '#AnonType_Presisering'."
            ),
            row(
                "too long Presisering",
                "<Kategori Kode=\"1.1\" Presisering=\"${"a".repeat(301)}\"/>",
                "cvc-maxLength-valid: Value '${"a".repeat(301)}' with length = '301' is not facet-valid " +
                        "with respect to maxLength '300' for type '#AnonType_Presisering'."
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
        private fun buildXmlInTest(kategoriXml: String): String = buildBarnevernXml(
            "<Tiltak Id=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" StartDato=\"2022-11-14\">" +
                    LOVHJEMMEL_XML +
                    kategoriXml +
                    "</Tiltak>"
        )
    }
}
