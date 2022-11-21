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
import no.ssb.barn.toStreamSource
import no.ssb.barn.util.ValidationUtils.getSchemaValidator
import org.xml.sax.SAXException

class SakTypeTest : BehaviorSpec({

    given("misc SakType XML") {

        /** make sure it's possible to make a valid test XML */
        `when`("valid XML, expect no exceptions") {
            shouldNotThrowAny {
                getSchemaValidator().validate(
                    buildXmlInTest(
                        "<Sak Id=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" " +
                                "MigrertId=\"~MigrertId~\" " +
                                "StartDato=\"2022-11-14\" " +
                                "SluttDato=\"2022-11-15\" " +
                                "Avsluttet=\"true\" " +
                                "Journalnummer=\"2022-00004\"" +
                                "/>"
                    ).toStreamSource()
                )
            }
        }

        forAll(
            /** Id */
            row(
                "missing Id",
                "<Sak StartDato=\"2022-11-14\" Journalnummer=\"2022-00004\"/>",
                "cvc-complex-type.4: Attribute 'Id' must appear on element 'Sak'."
            ),
            row(
                "empty Id",
                "<Sak Id=\"\" StartDato=\"2022-11-14\" Journalnummer=\"2022-00004\"/>",
                EMPTY_ID_ERROR
            ),
            row(
                "invalid Id",
                "<Sak Id=\"42\" StartDato=\"2022-11-14\" Journalnummer=\"2022-00004\"/>",
                INVALID_ID_ERROR
            ),

            /** MigrertId */
            row(
                "empty MigrertId",
                "<Sak Id=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" MigrertId=\"\" StartDato=\"2022-11-14\" " +
                        "Journalnummer=\"2022-00004\"/>",
                "cvc-minLength-valid: Value '' with length = '0' is not facet-valid with respect to minLength '1' " +
                        "for type '#AnonType_MigrertId'."
            ),
            row(
                "too long MigrertId",
                "<Sak Id=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" MigrertId=\"${"a".repeat(37)}\" " +
                        "StartDato=\"2022-11-14\" Journalnummer=\"2022-00004\"/>",
                "cvc-maxLength-valid: Value '${"a".repeat(37)}' with length = '37' is not facet-valid with " +
                        "respect to maxLength '36' for type '#AnonType_MigrertId'."
            ),

            /** StartDato */
            row(
                "missing StartDato",
                "<Sak Id=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" Journalnummer=\"2022-00004\"/>",
                "cvc-complex-type.4: Attribute 'StartDato' must appear on element 'Sak'."
            ),
            row(
                "empty StartDato",
                "<Sak Id=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" StartDato=\"\" Journalnummer=\"2022-00004\"/>",
                EMPTY_DATE_ERROR
            ),
            row(
                "invalid StartDato",
                "<Sak Id=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" StartDato=\"2022\" Journalnummer=\"2022-00004\"/>",
                INVALID_DATE_ERROR
            ),

            /** SluttDato */
            row(
                "empty SluttDato",
                "<Sak Id=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" StartDato=\"2022-11-14\" SluttDato=\"\" " +
                        "Journalnummer=\"2022-00004\"/>",
                EMPTY_DATE_ERROR
            ),
            row(
                "invalid SluttDato",
                "<Sak Id=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" StartDato=\"2022-11-14\" SluttDato=\"2022\" " +
                        "Journalnummer=\"2022-00004\"/>",
                INVALID_DATE_ERROR
            ),

            /** Avsluttet */
            row(
                "empty Avsluttet",
                "<Sak Id=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" StartDato=\"2022-11-14\" Avsluttet=\"\" " +
                        "Journalnummer=\"2022-00004\"/>",
                "cvc-datatype-valid.1.2.1: '' is not a valid value for 'boolean'."
            ),
            row(
                "invalid Avsluttet",
                "<Sak Id=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" StartDato=\"2022-11-14\" Avsluttet=\"True\" " +
                        "Journalnummer=\"2022-00004\"/>",
                "cvc-datatype-valid.1.2.1: 'True' is not a valid value for 'boolean'."
            ),

            /** Journalnummer */
            row(
                "missing Journalnummer",
                "<Sak Id=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" StartDato=\"2022-11-14\"/>",
                "cvc-complex-type.4: Attribute 'Journalnummer' must appear on element 'Sak'."
            ),
            row(
                "empty Journalnummer",
                "<Sak Id=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" StartDato=\"2022-11-14\" " +
                        "Journalnummer=\"\"/>",
                "cvc-minLength-valid: Value '' with length = '0' is not facet-valid with respect to minLength '1' " +
                        "for type '#AnonType_JournalnummerSakType'."
            ),
            row(
                "too long Journalnummer",
                "<Sak Id=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" StartDato=\"2022-11-14\" " +
                        "Journalnummer=\"${"a".repeat(37)}\"/>",
                "cvc-maxLength-valid: Value '${"a".repeat(37)}' with length = '37' is not facet-valid with " +
                        "respect to maxLength '36' for type '#AnonType_JournalnummerSakType'."
            ),
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
        private fun buildXmlInTest(sakElement: String): String =
            "<Barnevern Id=\"236110fc-edba-4b86-87b3-d6bb945cbc76\" DatoUttrekk=\"2022-11-14T15:13:33+01:00\">" +
                    "<Fagsystem Leverandor=\"Netcompany\" Navn=\"Modulus Barn\" Versjon=\"1\" />" +
                    "<Avgiver Organisasjonsnummer=\"999999999\" Kommunenummer=\"1234\" Kommunenavn=\"En kommune\"/>" +
                    sakElement +
                    "</Barnevern>"
    }
}
