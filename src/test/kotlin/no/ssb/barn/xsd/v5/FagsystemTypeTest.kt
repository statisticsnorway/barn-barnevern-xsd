package no.ssb.barn.xsd.v5

import io.kotest.assertions.throwables.shouldNotThrowAny
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.data.forAll
import io.kotest.data.row
import io.kotest.matchers.string.shouldContain
import no.ssb.barn.TestUtils.VALID_DATE
import no.ssb.barn.TestUtils.VALID_DATO_UTTREKK
import no.ssb.barn.toStreamSource
import no.ssb.barn.util.ValidationUtils.getSchemaValidatorV5
import org.xml.sax.SAXException

class FagsystemTypeTest : BehaviorSpec({
	Given("misc Fagsystem XML") {
		When("valid Fagsystem, expect no validation errors") {
			shouldNotThrowAny {
				val inner = "<Fagsystem><Leverandor>Netcompany</Leverandor><Navn>Modulus Barn</Navn><Versjon>1</Versjon></Fagsystem>"
				getSchemaValidatorV5().validate(buildXml(inner).toStreamSource())
			}
		}

		forAll(
			row(
				"missing Leverandor",
				"<Fagsystem><Navn>Modulus Barn</Navn><Versjon>1</Versjon></Fagsystem>",
				"Leverandor"
			),
			row(
				"missing Navn",
				"<Fagsystem><Leverandor>Netcompany</Leverandor><Versjon>1</Versjon></Fagsystem>",
				"Navn"
			),
			row(
				"missing Versjon",
				"<Fagsystem><Leverandor>Netcompany</Leverandor><Navn>Modulus Barn</Navn></Fagsystem>",
				"Versjon"
			)
		) { description, partialXml, expectedFragment ->
			When(description) {
				val thrown = shouldThrow<SAXException> {
					getSchemaValidatorV5().validate(buildXml(partialXml).toStreamSource())
				}

				Then("thrown should contain expected validation details") {
					thrown.message.orEmpty() shouldContain expectedFragment
				}
			}
		}
	}
}) {
    companion object {

        private fun buildXml(fagsystemXml: String) = "<BarnevernregisterInnrapportering>" +
            "<Id>236110fc-edba-4b86-87b3-d6bb945cbc76</Id>" +
            "<DatoUttrekk>$VALID_DATO_UTTREKK</DatoUttrekk>" +
            fagsystemXml +
            "<Avgiver><Organisasjonsnummer>999999999</Organisasjonsnummer><Kommunenummer>1234</Kommunenummer><Kommunenavn>~Kommunenavn~</Kommunenavn></Avgiver>" +
            "<Sak><id>6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e</id><StartDato>$VALID_DATE</StartDato><Journalnummer>2022-00004</Journalnummer>" +
            "</Sak></BarnevernregisterInnrapportering>"
    }
}
