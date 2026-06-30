package no.ssb.barn.xsd.v5

import io.kotest.assertions.throwables.shouldNotThrowAny
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.data.forAll
import io.kotest.data.row
import io.kotest.matchers.string.shouldContain
import no.ssb.barn.TestUtils.VALID_DATE
import no.ssb.barn.TestUtils.INVALID_DATE
import no.ssb.barn.TestUtils.EMPTY_DATE_ERROR
import no.ssb.barn.TestUtils.INVALID_DATE_FORMAT_ERROR
import no.ssb.barn.toStreamSource
import no.ssb.barn.util.ValidationUtils.getSchemaValidatorV5
import org.xml.sax.SAXException

class BrukAvPolitiTypeTest : BehaviorSpec({
	Given("misc BrukAvPoliti XML") {
		When("valid BrukAvPoliti, expect no validation errors") {
			shouldNotThrowAny {
				val inner = "<BrukAvPoliti><Dato>$VALID_DATE</Dato></BrukAvPoliti>"
				getSchemaValidatorV5().validate(buildXml(inner).toStreamSource())
			}
		}

		forAll(
			row(
				"missing Dato",
				"<BrukAvPoliti></BrukAvPoliti>",
				"Dato"
			),
			row(
				"empty Dato",
				"<BrukAvPoliti><Dato></Dato></BrukAvPoliti>",
				EMPTY_DATE_ERROR
			),
			row(
				"invalid Dato",
				"<BrukAvPoliti><Dato>$INVALID_DATE</Dato></BrukAvPoliti>",
				INVALID_DATE_FORMAT_ERROR
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
		private fun buildXml(brukAvPolitiXml: String) = no.ssb.barn.TestUtils.buildBarnevernXmlV5("<Tiltak>" +
			"<Id>6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e</Id>" +
			"<StartDato>$VALID_DATE</StartDato>" +
			"<Lovhjemmel><Lov>BVL</Lov><Kapittel>1</Kapittel><Paragraf>2</Paragraf></Lovhjemmel>" +
			"<Kode>2.1</Kode>" +
			brukAvPolitiXml +
			"</Tiltak>")
	}
}
