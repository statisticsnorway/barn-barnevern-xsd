package no.ssb.barn.xsd

import io.kotest.assertions.throwables.shouldNotThrowAny
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.data.forAll
import io.kotest.data.row
import io.kotest.matchers.shouldBe
import no.ssb.barn.TestUtils.EMPTY_ID_ERROR
import no.ssb.barn.TestUtils.INVALID_DATE
import no.ssb.barn.TestUtils.INVALID_DATO_UTTREKK
import no.ssb.barn.TestUtils.INVALID_ID_ERROR
import no.ssb.barn.TestUtils.VALID_DATE
import no.ssb.barn.TestUtils.VALID_DATO_UTTREKK
import no.ssb.barn.toStreamSource
import no.ssb.barn.util.ValidationUtils.getSchemaValidatorV3
import org.xml.sax.SAXException
import java.time.Year

class BarnevernTypeTest : BehaviorSpec({

    Given("misc Barnevern XML") {

        /** make sure it's possible to make a valid test XML */

        forAll(
            row("zone offset = +01:00", VALID_DATO_UTTREKK),
            row("zone offset = Z", "${Year.now()}-11-14T15:13:33Z"),
            row("decimals and zone offset = +01:00", "${Year.now()}-11-14T15:13:33.123456789+01:00"),
            row("decimals and zone offset = Z", "${Year.now()}-11-14T15:13:33.123456789Z"),
        ) { description, dateTimeString ->
            When(description) {
                shouldNotThrowAny {
                    getSchemaValidatorV3().validate(
                        buildXmlInTest(
                            "<Barnevern Id=\"236110fc-edba-4b86-87b3-d6bb945cbc76\" " +
                                    "ForrigeId=\"236110fc-edba-4b86-87b3-d6bb945cbc76\" " +
                                    "DatoUttrekk=\"$dateTimeString\">"
                        ).toStreamSource()
                    )
                }
            }
        }

        forAll(
            /** Id */
            row(
                "missing Id",
                "<Barnevern DatoUttrekk=\"$VALID_DATO_UTTREKK\">",
                "cvc-complex-type.4: Attribute 'Id' must appear on element 'Barnevern'."
            ),
            row(
                "empty Id",
                "<Barnevern Id=\"\" DatoUttrekk=\"$VALID_DATO_UTTREKK\">",
                EMPTY_ID_ERROR
            ),
            row(
                "invalid Id",
                "<Barnevern Id=\"42\" DatoUttrekk=\"$VALID_DATO_UTTREKK\">",
                INVALID_ID_ERROR
            ),

            /** ForrigeId */
            row(
                "empty ForrigeId",
                "<Barnevern Id=\"236110fc-edba-4b86-87b3-d6bb945cbc76\" ForrigeId=\"\" " +
                        "DatoUttrekk=\"$VALID_DATO_UTTREKK\">",
                "cvc-pattern-valid: Value '' is not facet-valid with respect to pattern " +
                        "'[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[1-5][0-9a-fA-F]{3}-[89abAB][0-9a-fA-F]{3}-[0-9a-fA-F]{12}' " +
                        "for type '#AnonType_ForrigeIdBarnevernType'."
            ),
            row(
                "invalid ForrigeId",
                "<Barnevern Id=\"236110fc-edba-4b86-87b3-d6bb945cbc76\" ForrigeId=\"42\" " +
                        "DatoUttrekk=\"$VALID_DATO_UTTREKK\">",
                "cvc-pattern-valid: Value '42' is not facet-valid with respect to pattern " +
                        "'[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[1-5][0-9a-fA-F]{3}-[89abAB][0-9a-fA-F]{3}-[0-9a-fA-F]{12}' " +
                        "for type '#AnonType_ForrigeIdBarnevernType'."
            ),

            /** DatoUttrekk */
            row(
                "missing DatoUttrekk",
                "<Barnevern Id=\"236110fc-edba-4b86-87b3-d6bb945cbc76\">",
                "cvc-complex-type.4: Attribute 'DatoUttrekk' must appear on element 'Barnevern'."
            ),
            row(
                "empty DatoUttrekk",
                "<Barnevern Id=\"236110fc-edba-4b86-87b3-d6bb945cbc76\" DatoUttrekk=\"\">",
                "cvc-pattern-valid: Value '' is not facet-valid with respect to pattern " +
                        "'\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}(\\.\\d{1,9})?(Z|[+-]\\d{2}:\\d{2})' " +
                        "for type '#AnonType_DatoUttrekkBarnevernType'."
            ),
            row(
                "invalid DatoUttrekk",
                "<Barnevern Id=\"236110fc-edba-4b86-87b3-d6bb945cbc76\" DatoUttrekk=\"$INVALID_DATE\">",
                "cvc-pattern-valid: Value '$INVALID_DATE' is not facet-valid with respect to pattern " +
                        "'\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}(\\.\\d{1,9})?(Z|[+-]\\d{2}:\\d{2})' " +
                        "for type '#AnonType_DatoUttrekkBarnevernType'."
            ),
            row(
                "invalid DatoUttrekk, zone is missing",
                "<Barnevern Id=\"236110fc-edba-4b86-87b3-d6bb945cbc76\" DatoUttrekk=\"$INVALID_DATO_UTTREKK\">",
                "cvc-pattern-valid: Value '$INVALID_DATO_UTTREKK' is not facet-valid with respect to pattern " +
                        "'\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}(\\.\\d{1,9})?(Z|[+-]\\d{2}:\\d{2})' " +
                        "for type '#AnonType_DatoUttrekkBarnevernType'."
            )
        ) { description, sakElement, expectedError ->
            When(description) {
                val thrown = shouldThrow<SAXException> {
                    getSchemaValidatorV3().validate(buildXmlInTest(sakElement).toStreamSource())
                }

                Then("thrown should be as expected") {
                    thrown.message shouldBe expectedError
                }
            }
        }
    }
}) {
    companion object {
        private fun buildXmlInTest(startTag: String): String =
            startTag +
                    "<Fagsystem Leverandor=\"Netcompany\" Navn=\"Modulus Barn\" Versjon=\"1\" />" +
                    "<Avgiver Organisasjonsnummer=\"999999999\" Kommunenummer=\"1234\" Kommunenavn=\"En kommune\"/>" +
                    "<Sak Id=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" " +
                    "StartDato=\"$VALID_DATE\" Journalnummer=\"00004\" /></Barnevern>"
    }
}
