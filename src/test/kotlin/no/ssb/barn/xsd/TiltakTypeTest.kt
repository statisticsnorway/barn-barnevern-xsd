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

class TiltakTypeTest : BehaviorSpec({

    Given("misc TiltakType XML") {

        /** make sure it's possible to make a valid test XML */
        When("valid XML, expect no exceptions") {
            shouldNotThrowAny {
                getSchemaValidator().validate(
                    buildBarnevernXml(
                        "<Tiltak Id=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" MigrertId=\"1234\" " +
                                "StartDato=\"$VALID_DATE\">" +
                                LOVHJEMMEL_XML +
                                "<Kategori Kode=\"1.1\"/>" +
                                "</Tiltak>"
                    ).toStreamSource()
                )
            }
        }

        forAll(
            /** Id */

            row(
                "duplicate Id",
                "<Tiltak Id=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" StartDato=\"$VALID_DATE\">" +
                        LOVHJEMMEL_XML +
                        "<Kategori Kode=\"1.1\"/>" +
                        "</Tiltak>" +
                        "<Tiltak Id=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" StartDato=\"$VALID_DATE\">" +
                        LOVHJEMMEL_XML +
                        "<Kategori Kode=\"1.1\"/>" +
                        "</Tiltak>",
                "cvc-identity-constraint.4.1: Duplicate unique value [6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e] " +
                        "declared for identity constraint \"TiltakIdUnique\" of element \"Sak\"."
            ),

            row(
                "missing Id",
                "<Tiltak " +
                        "StartDato=\"$VALID_DATE\">" +
                        LOVHJEMMEL_XML +
                        "<Kategori Kode=\"1.1\"/>" +
                        "</Tiltak>",
                "cvc-complex-type.4: Attribute 'Id' must appear on element 'Tiltak'."
            ),
            row(
                "empty Id",
                "<Tiltak Id=\"\" " +
                        "StartDato=\"$VALID_DATE\">" +
                        LOVHJEMMEL_XML +
                        "<Kategori Kode=\"1.1\"/>" +
                        "</Tiltak>",
                EMPTY_ID_ERROR
            ),
            row(
                "invalid Id",
                "<Tiltak Id=\"42\" " +
                        "StartDato=\"$VALID_DATE\">" +
                        LOVHJEMMEL_XML +
                        "<Kategori Kode=\"1.1\"/>" +
                        "</Tiltak>",
                INVALID_ID_ERROR
            ),

            /** MigrertId */
            row(
                "empty MigrertId",
                "<Tiltak Id=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" MigrertId=\"\" " +
                        "StartDato=\"$VALID_DATE\">" +
                        LOVHJEMMEL_XML +
                        "<Kategori Kode=\"1.1\"/>" +
                        "</Tiltak>",
                "cvc-minLength-valid: Value '' with length = '0' is not facet-valid with respect to minLength '1' " +
                        "for type '#AnonType_MigrertId'."
            ),
            row(
                "too long MigrertId",
                "<Tiltak Id=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" MigrertId=\"${"a".repeat(37)}\" " +
                        "StartDato=\"$VALID_DATE\">" +
                        LOVHJEMMEL_XML +
                        "<Kategori Kode=\"1.1\"/>" +
                        "</Tiltak>",
                "cvc-maxLength-valid: Value '${"a".repeat(37)}' with length = '37' is not facet-valid with " +
                        "respect to maxLength '36' for type '#AnonType_MigrertId'."
            ),

            /** StartDato */
            row(
                "missing StartDato",
                "<Tiltak Id=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\">" +
                        LOVHJEMMEL_XML +
                        "<Kategori Kode=\"1.1\"/>" +
                        "</Tiltak>",
                "cvc-complex-type.4: Attribute 'StartDato' must appear on element 'Tiltak'."
            ),
            row(
                "empty StartDato",
                "<Tiltak Id=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" " +
                        "StartDato=\"\">" +
                        LOVHJEMMEL_XML +
                        "<Kategori Kode=\"1.1\"/>" +
                        "</Tiltak>",
                EMPTY_DATE_ERROR
            ),
            row(
                "invalid StartDato",
                "<Tiltak Id=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" " +
                        "StartDato=\"$INVALID_DATE\">" +
                        LOVHJEMMEL_XML +
                        "<Kategori Kode=\"1.1\"/>" +
                        "</Tiltak>",
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
    }
})