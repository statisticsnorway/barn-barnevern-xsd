package no.ssb.barn.util

import io.kotest.assertions.throwables.shouldNotThrowAny
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import no.ssb.barn.util.ValidationUtils.VERSION_ONE_XSD
import no.ssb.barn.util.ValidationUtils.getSchemaValidator
import org.xml.sax.SAXException
import java.io.StringReader
import javax.xml.transform.stream.StreamSource

class ValidationUtilsTest : BehaviorSpec({

    Given("XML that should not validate because of Melder Kode=\"\" (BAR-693)") {
        When("validate XML") {
            val thrown = shouldThrow<SAXException> {
                getSchemaValidator().validate(StreamSource(StringReader(testXml(""))))
            }

            then("thrown should be as expected") {
                thrown.message shouldBe
                        "cvc-minLength-valid: Value '' with length = '0' is not facet-valid with respect to " +
                        "minLength '1' for type '#AnonType_KodeMelderType'."
            }
        }
    }

    Given("XML that should validate") {
        When("validate XML") {
            shouldNotThrowAny {
                getSchemaValidator().validate(StreamSource(StringReader(testXml())))
            }
        }
    }

    Given("existing XSD") {
        When("getSchemaValidator") {
            val schemaValidator = shouldNotThrowAny {
                getSchemaValidator()
            }

            Then("schemaValidator should not be null") {
                schemaValidator.shouldNotBeNull()
            }
        }

        When("getSchemaValidator with resource name") {
            val schemaValidator = shouldNotThrowAny {
                getSchemaValidator(VERSION_ONE_XSD)
            }

            Then("schemaValidator should not be null") {
                schemaValidator.shouldNotBeNull()
            }
        }
    }
}) {
    companion object {

        private fun testXml(melderKode: String = "1") = "<Barnevern Id=\"236110fc-edba-4b86-87b3-d6bb945cbc76\" " +
                "DatoUttrekk=\"2022-11-14T15:13:33.1077852+01:00\">" +
                "<Fagsystem Leverandor=\"Netcompany\" Navn=\"Modulus Barn\" Versjon=\"1\" />" +
                "<Avgiver Organisasjonsnummer=\"999999999\" Kommunenummer=\"1234\" Kommunenavn=\"En kommune\" />" +
                "<Sak Id=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" StartDato=\"2022-11-14\" " +
                "Journalnummer=\"2022-00004\"><Melding Id=\"e16bec92-70fe-4313-957b-81430aced812\" " +
                "StartDato=\"2022-11-14\"><Melder Kode=\"$melderKode\" /></Melding></Sak></Barnevern>"
    }
}
