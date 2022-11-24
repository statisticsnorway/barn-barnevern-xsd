package no.ssb.barn.xsd

import io.kotest.assertions.throwables.shouldNotThrowAny
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.data.forAll
import io.kotest.data.row
import io.kotest.matchers.shouldBe
import no.ssb.barn.TestUtils.EMPTY_ID_ERROR
import no.ssb.barn.TestUtils.INVALID_ID_ERROR
import no.ssb.barn.toStreamSource
import no.ssb.barn.util.ValidationUtils.getSchemaValidator
import org.xml.sax.SAXException

class BarnevernTypeTest : BehaviorSpec({

    given("misc Barnevern XML") {

        /** make sure it's possible to make a valid test XML */
        `when`("valid XML, expect no exceptions") {
            shouldNotThrowAny {
                getSchemaValidator().validate(
                    buildXmlInTest(
                        "<Barnevern Id=\"236110fc-edba-4b86-87b3-d6bb945cbc76\" " +
                                "ForrigeId=\"236110fc-edba-4b86-87b3-d6bb945cbc76\" " +
                                "DatoUttrekk=\"2022-11-14T15:13:33+01:00\">"
                    ).toStreamSource()
                )
            }
        }

        forAll(
            /** Id */
            row(
                "missing Id",
                "<Barnevern DatoUttrekk=\"2022-11-14T15:13:33+01:00\">",
                "cvc-complex-type.4: Attribute 'Id' must appear on element 'Barnevern'."
            ),
            row(
                "empty Id",
                "<Barnevern Id=\"\" DatoUttrekk=\"2022-11-14T15:13:33+01:00\">",
                EMPTY_ID_ERROR
            ),
            row(
                "invalid Id",
                "<Barnevern Id=\"42\" DatoUttrekk=\"2022-11-14T15:13:33+01:00\">",
                INVALID_ID_ERROR
            ),

            /** ForrigeId */
            row(
                "empty ForrigeId",
                "<Barnevern Id=\"236110fc-edba-4b86-87b3-d6bb945cbc76\" ForrigeId=\"\" " +
                        "DatoUttrekk=\"2022-11-14T15:13:33+01:00\">",
                "cvc-pattern-valid: Value '' is not facet-valid with respect to pattern " +
                        "'[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[1-5][0-9a-fA-F]{3}-[89abAB][0-9a-fA-F]{3}-[0-9a-fA-F]{12}' " +
                        "for type '#AnonType_ForrigeIdBarnevernType'."
            ),
            row(
                "invalid ForrigeId",
                "<Barnevern Id=\"236110fc-edba-4b86-87b3-d6bb945cbc76\" ForrigeId=\"42\" " +
                        "DatoUttrekk=\"2022-11-14T15:13:33+01:00\">",
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
                "cvc-datatype-valid.1.2.1: '' is not a valid value for 'dateTime'."
            ),
            row(
                "invalid DatoUttrekk",
                "<Barnevern Id=\"236110fc-edba-4b86-87b3-d6bb945cbc76\" DatoUttrekk=\"2022\">",
                "cvc-datatype-valid.1.2.1: '2022' is not a valid value for 'dateTime'."
            )
        ) { description, sakElement, expectedError ->
            `when`(description) {
                val thrown = shouldThrow<SAXException> {
                    getSchemaValidator().validate(buildXmlInTest(sakElement).toStreamSource())
                }

                then("thrown should be as expected") {
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
                    "StartDato=\"2022-11-14\" Journalnummer=\"2022-00004\" /></Barnevern>"
    }
}
