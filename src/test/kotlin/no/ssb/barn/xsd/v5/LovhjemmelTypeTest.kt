package no.ssb.barn.xsd.v5

import io.kotest.assertions.throwables.shouldNotThrowAny
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.data.forAll
import io.kotest.data.row
import io.kotest.matchers.string.shouldContain
import no.ssb.barn.TestUtils.VALID_DATE
import no.ssb.barn.TestUtils.buildBarnevernXmlV5
import no.ssb.barn.toStreamSource
import no.ssb.barn.util.ValidationUtils.getSchemaValidatorV5
import org.xml.sax.SAXException

class LovhjemmelTypeTest : BehaviorSpec({
	Given("misc Lovhjemmel XML") {
		When("valid Lovhjemmel inside Tiltak, expect no validation errors") {
			shouldNotThrowAny {
				val inner = "<Tiltak><Id>6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e</Id><StartDato>$VALID_DATE</StartDato>" +
					"<Lovhjemmel><Lov>BVL</Lov><Kapittel>1</Kapittel><Paragraf>2</Paragraf></Lovhjemmel><Kode>2.1</Kode></Tiltak>"
				getSchemaValidatorV5().validate(buildBarnevernXmlV5(inner).toStreamSource())
			}
		}

		forAll(
			row(
				"missing Lov",
				"<Tiltak><Id>6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e</Id><StartDato>$VALID_DATE</StartDato><Lovhjemmel><Kapittel>1</Kapittel><Paragraf>2</Paragraf></Lovhjemmel><Kode>2.1</Kode></Tiltak>",
				"Lov"
			),
			row(
				"missing Kapittel",
				"<Tiltak><Id>6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e</Id><StartDato>$VALID_DATE</StartDato><Lovhjemmel><Lov>BVL</Lov><Paragraf>2</Paragraf></Lovhjemmel><Kode>2.1</Kode></Tiltak>",
				"Kapittel"
			),
			row(
				"missing Paragraf",
				"<Tiltak><Id>6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e</Id><StartDato>$VALID_DATE</StartDato><Lovhjemmel><Lov>BVL</Lov><Kapittel>1</Kapittel></Lovhjemmel><Kode>2.1</Kode></Tiltak>",
				"Paragraf"
			)
		) { description, partialXml, expectedFragment ->
			When(description) {
				val thrown = shouldThrow<SAXException> {
					getSchemaValidatorV5().validate(buildBarnevernXmlV5(partialXml).toStreamSource())
				}

				Then("thrown should contain expected validation details") {
					thrown.message.orEmpty() shouldContain expectedFragment
				}
			}
		}
	}
})
