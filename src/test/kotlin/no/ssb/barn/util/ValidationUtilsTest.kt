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

    given("XML that should not validate because of Melder Kode=\"\" (BAR-693)") {
        val xml =
            "<Barnevern Id=\"236110fc-edba-4b86-87b3-d6bb945cbc76\" DatoUttrekk=\"2022-11-14T15:13:33.1077852+01:00\"><Fagsystem Leverandor=\"Netcompany\" Navn=\"Modulus Barn\" Versjon=\"1\" /><Avgiver Organisasjonsnummer=\"111111111\" Kommunenummer=\"0301\" Kommunenavn=\"Digi Oslo kommune\" Bydelsnummer=\"01\" Bydelsnavn=\"Digi Oslo kommune Bydel 01\" /><Sak Id=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" StartDato=\"2022-11-14\" Journalnummer=\"2022-00004\"><Personalia Id=\"d1ce1da7-d346-422e-94cb-a8248aa1fcd3\" StartDato=\"2022-11-14\" Fodselsnummer=\"14112200200\" Fodseldato=\"2022-11-14\" Kjonn=\"0\" /><Melding Id=\"e16bec92-70fe-4313-957b-81430aced812\" StartDato=\"2022-11-14\"><Melder Kode=\"\" /><Melder Kode=\"2\" /><Saksinnhold Kode=\"6\" /><Saksinnhold Kode=\"7\" /><Saksinnhold Kode=\"18\" Presisering=\"sdfsdf\" /></Melding></Sak></Barnevern>"

        `when`("validate XML") {
            val thrown = shouldThrow<SAXException> {
                getSchemaValidator().validate(StreamSource(StringReader(xml)))
            }

            then ("thrown should be as expected"){
                thrown.message shouldBe
                        "cvc-minLength-valid: Value '' with length = '0' is not facet-valid with respect to minLength '1' for type '#AnonType_KodeMelderType'."
            }
        }
    }

    given("XML that should validate") {
        val xml =
            "<Barnevern Id=\"236110fc-edba-4b86-87b3-d6bb945cbc76\" DatoUttrekk=\"2022-11-14T15:13:33.1077852+01:00\"><Fagsystem Leverandor=\"Netcompany\" Navn=\"Modulus Barn\" Versjon=\"1\" /><Avgiver Organisasjonsnummer=\"111111111\" Kommunenummer=\"0301\" Kommunenavn=\"Digi Oslo kommune\" Bydelsnummer=\"01\" Bydelsnavn=\"Digi Oslo kommune Bydel 01\" /><Sak Id=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" StartDato=\"2022-11-14\" Journalnummer=\"2022-00004\"><Personalia Id=\"d1ce1da7-d346-422e-94cb-a8248aa1fcd3\" StartDato=\"2022-11-14\" Fodselsnummer=\"14112200200\" Fodseldato=\"2022-11-14\" Kjonn=\"0\" /><Melding Id=\"e16bec92-70fe-4313-957b-81430aced812\" StartDato=\"2022-11-14\"><Melder Kode=\"1\" /><Melder Kode=\"2\" /><Saksinnhold Kode=\"6\" /><Saksinnhold Kode=\"7\" /><Saksinnhold Kode=\"18\" Presisering=\"sdfsdf\" /></Melding></Sak></Barnevern>"

        `when`("validate XML") {
            shouldNotThrowAny {
                getSchemaValidator().validate(StreamSource(StringReader(xml)))
            }
        }
    }

    given("existing XSD") {

        `when`("getSchemaValidator") {

            val schemaValidator = shouldNotThrowAny {
                getSchemaValidator()
            }

            then("schemaValidator should not be null") {
                schemaValidator.shouldNotBeNull()
            }
        }

        `when`("getSchemaValidator with resource name") {

            val schemaValidator = shouldNotThrowAny {
                getSchemaValidator(VERSION_ONE_XSD)
            }

            then("schemaValidator should not be null") {
                schemaValidator.shouldNotBeNull()
            }
        }
    }
})
