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
import no.ssb.barn.TestUtils.INVALID_MAX_DATE_TOO_LATE
import no.ssb.barn.TestUtils.INVALID_MIN_DATE_TOO_EARLY
import no.ssb.barn.TestUtils.LOVHJEMMEL_XML
import no.ssb.barn.TestUtils.MAX_DATE
import no.ssb.barn.TestUtils.MIN_DATE
import no.ssb.barn.TestUtils.VALID_DATE
import no.ssb.barn.TestUtils.buildBarnevernXml
import no.ssb.barn.toStreamSource
import no.ssb.barn.util.ValidationUtils.getSchemaValidator
import org.xml.sax.SAXException

class TilsynUtfortTypeTest : BehaviorSpec({

    Given("misc TilsynUtfort XML") {

        /** make sure it's possible to make a valid test XML */
        When("valid XML, expect no exceptions") {
            shouldNotThrowAny {
                getSchemaValidator().validate(
                    buildXmlInTest(
                        "<Utfort Id=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" " +
                                "UtfortDato=\"$VALID_DATE\" />"
                    ).toStreamSource()
                )
            }
        }

        forAll(
            /** Id */
            row(
                "missing Id",
                "<Utfort UtfortDato=\"$VALID_DATE\" />",
                "cvc-complex-type.4: Attribute 'Id' must appear on element 'Utfort'."
            ),
            row(
                "empty Id",
                "<Utfort Id=\"\" UtfortDato=\"$VALID_DATE\" />",
                EMPTY_ID_ERROR
            ),
            row(
                "invalid Id",
                "<Utfort Id=\"42\" UtfortDato=\"$VALID_DATE\" />",
                INVALID_ID_ERROR
            ),

            /** UtfortDato */
            row(
                "missing UtfortDato",
                "<Utfort Id=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" />",
                "cvc-complex-type.4: Attribute 'UtfortDato' must appear on element 'Utfort'."
            ),
            row(
                "empty UtfortDato",
                "<Utfort Id=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" UtfortDato=\"\" />",
                EMPTY_DATE_ERROR
            ),
            row(
                "invalid UtfortDato",
                "<Utfort Id=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" UtfortDato=\"$INVALID_DATE\" />",
                INVALID_DATE_FORMAT_ERROR
            ),
            row(
                "UtfortDato too early",
                "<Utfort Id=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" UtfortDato=\"$INVALID_MIN_DATE_TOO_EARLY\" />",
                "cvc-minInclusive-valid: Value '$INVALID_MIN_DATE_TOO_EARLY' is not facet-valid with respect to " +
                        "minInclusive '$MIN_DATE' for type '#AnonType_UtfortDatoTilsynUtfortType'."
            ),
            row(
                "UtfortDato too late",
                "<Utfort Id=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" UtfortDato=\"$INVALID_MAX_DATE_TOO_LATE\" />",
                "cvc-maxInclusive-valid: Value '$INVALID_MAX_DATE_TOO_LATE' is not facet-valid with respect to " +
                        "maxInclusive '$MAX_DATE' for type '#AnonType_UtfortDatoTilsynUtfortType'."
            )
        ) { description, partialXml, expectedError ->
            When(description) {
                val thrown = shouldThrow<SAXException> {
                    getSchemaValidator().validate(buildXmlInTest(partialXml).toStreamSource())
                }

                Then("thrown should be as expected") {
                    thrown.message shouldBe expectedError
                }
            }
        }
    }
}) {
    companion object {
        private fun buildXmlInTest(tilsynUtfortXml: String): String = buildBarnevernXml(
            "<Tiltak Id=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" StartDato=\"$VALID_DATE\">" +
                    LOVHJEMMEL_XML +
                    "<Kategori Kode=\"1.1\" /><Tilsyn>" +
                    "<Ansvarlig Id=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" " +
                    "StartDato=\"$VALID_DATE\" Kommunenummer=\"1234\"/>" +
                    "<Hyppighet Id=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" " +
                    "StartDato=\"$VALID_DATE\" Kode=\"2\"/>" +
                    tilsynUtfortXml +
                    "</Tilsyn></Tiltak>"
        )
    }
}
