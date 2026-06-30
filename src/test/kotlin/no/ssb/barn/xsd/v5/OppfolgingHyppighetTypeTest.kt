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

class OppfolgingHyppighetTypeTest : BehaviorSpec({
	Given("misc OppfolgingHyppighet XML") {
		When("valid OppfolgingHyppighet, expect no validation errors") {
			shouldNotThrowAny {
				val hy = "<Hyppighet><Id>6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e</Id><Dato>$VALID_DATE</Dato><HyppighetTidsenhet>1</HyppighetTidsenhet><HyppighetAntall>4</HyppighetAntall></Hyppighet>"
				getSchemaValidatorV5().validate(buildXml(hy).toStreamSource())
			}
		}

		forAll(
			row(
				"missing Id",
				"<Hyppighet><Dato>$VALID_DATE</Dato><HyppighetTidsenhet>1</HyppighetTidsenhet><HyppighetAntall>4</HyppighetAntall></Hyppighet>",
				"Id"
			),
			row(
				"missing Dato",
				"<Hyppighet><Id>6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e</Id><HyppighetTidsenhet>1</HyppighetTidsenhet><HyppighetAntall>4</HyppighetAntall></Hyppighet>",
				"Dato"
			),
			row(
				"empty Dato",
				"<Hyppighet><Id>6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e</Id><Dato></Dato><HyppighetTidsenhet>1</HyppighetTidsenhet><HyppighetAntall>4</HyppighetAntall></Hyppighet>",
				EMPTY_DATE_ERROR
			),
			row(
				"invalid Dato",
				"<Hyppighet><Id>6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e</Id><Dato>$INVALID_DATE</Dato><HyppighetTidsenhet>1</HyppighetTidsenhet><HyppighetAntall>4</HyppighetAntall></Hyppighet>",
				INVALID_DATE_FORMAT_ERROR
			),
			row(
				"missing HyppighetTidsenhet",
				"<Hyppighet><Id>6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e</Id><Dato>$VALID_DATE</Dato><HyppighetAntall>4</HyppighetAntall></Hyppighet>",
				"HyppighetTidsenhet"
			),
			row(
				"missing HyppighetAntall",
				"<Hyppighet><Id>6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e</Id><Dato>$VALID_DATE</Dato><HyppighetTidsenhet>1</HyppighetTidsenhet></Hyppighet>",
				"HyppighetAntall"
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
		private fun buildXml(hyppighetXml: String) = no.ssb.barn.TestUtils.buildBarnevernXmlV5("<Tiltak>" +
			"<Id>6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e</Id>" +
			"<StartDato>2023-11-14</StartDato>" +
			"<Lovhjemmel><Lov>BVL</Lov><Kapittel>1</Kapittel><Paragraf>2</Paragraf></Lovhjemmel>" +
			"<Kode>2.1</Kode>" +
			"<Oppfolging>" + hyppighetXml + "</Oppfolging>" +
			"</Tiltak>")
	}
}