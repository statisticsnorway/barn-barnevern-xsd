package no.ssb.barn.xsd

import io.kotest.assertions.throwables.shouldNotThrowAny
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.data.forAll
import io.kotest.data.row
import io.kotest.matchers.shouldBe
import no.ssb.barn.toStreamSource
import no.ssb.barn.util.ValidationUtils.getSchemaValidator
import org.xml.sax.SAXException

class MeldingTypeTest : BehaviorSpec({

    given("misc MeldingType XML") {

        /** make sure it's possible to make a valid test XML */
        `when`("valid XML, expected no exceptions") {
            shouldNotThrowAny {
                getSchemaValidator().validate(buildXmlInTest(
                    "<Melding Id=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" MigrertId=\"1234\" " +
                            "StartDato=\"2022-11-14\"/>").toStreamSource())
            }
        }

        forAll(
            /** Id */
            row(
                "missing Id",
                "<Melding StartDato=\"2022-11-14\"/>",
                "cvc-complex-type.4: Attribute 'Id' must appear on element 'Melding'."
            ),
            row(
                "empty Id",
                "<Melding Id=\"\" StartDato=\"2022-11-14\"/>",
                "cvc-pattern-valid: Value '' is not facet-valid with respect to pattern " +
                        "'[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[1-5][0-9a-fA-F]{3}-[89abAB][0-9a-fA-F]{3}-[0-9a-fA-F]{12}' " +
                        "for type '#AnonType_Id'."
            ),
            row(
                "invalid Id",
                "<Melding Id=\"42\" StartDato=\"2022-11-14\"/>",
                "cvc-pattern-valid: Value '42' is not facet-valid with respect to pattern " +
                        "'[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[1-5][0-9a-fA-F]{3}-[89abAB][0-9a-fA-F]{3}-[0-9a-fA-F]{12}' " +
                        "for type '#AnonType_Id'."
            ),

            /** MigrertId */
            row(
                "empty MigrertId",
                "<Melding Id=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" MigrertId=\"\" StartDato=\"2022-11-14\"/>",
                "cvc-minLength-valid: Value '' with length = '0' is not facet-valid with respect to minLength '1' " +
                        "for type '#AnonType_MigrertId'."
            ),
            row(
                "too long MigrertId",
                "<Melding Id=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" MigrertId=\"${"a".repeat(37)}\" " +
                        "StartDato=\"2022-11-14\"/>",
                "cvc-maxLength-valid: Value '${"a".repeat(37)}' with length = '37' is not facet-valid with " +
                        "respect to maxLength '36' for type '#AnonType_MigrertId'."
            ),

            /** StartDato */
            row(
                "missing StartDato",
                "<Melding Id=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\"/>",
                "cvc-complex-type.4: Attribute 'StartDato' must appear on element 'Melding'."
            ),
            row(
                "empty StartDato",
                "<Melding Id=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" StartDato=\"\"/>",
                "cvc-datatype-valid.1.2.1: '' is not a valid value for 'date'."
            ),
            row(
                "invalid StartDato",
                "<Melding Id=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" StartDato=\"2022\"/>",
                "cvc-datatype-valid.1.2.1: '2022' is not a valid value for 'date'."
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
        private fun buildXmlInTest(meldingXml: String): String =
            "<Barnevern Id=\"236110fc-edba-4b86-87b3-d6bb945cbc76\" DatoUttrekk=\"2022-11-14T15:13:33.1077852+01:00\">" +
                    "<Fagsystem Leverandor=\"Netcompany\" Navn=\"Modulus Barn\" Versjon=\"1\"/>" +
                    "<Avgiver Organisasjonsnummer=\"111111111\" Kommunenummer=\"1234\" Kommunenavn=\"~Kommunenavn~\"/>" +
                    "<Sak Id=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" StartDato=\"2022-11-14\" Journalnummer=\"2022-00004\">" +
                    meldingXml +
                    "</Sak></Barnevern>"
    }
}
