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
import no.ssb.barn.TestUtils.buildBarnevernXmlV5
import no.ssb.barn.toStreamSource
import no.ssb.barn.util.ValidationUtils.getSchemaValidatorV5
import org.xml.sax.SAXException

class OversendelseBarneverntjenesteTypeTest : BehaviorSpec({
	Given("misc OversendelseBarneverntjeneste XML") {
		When("valid OversendelseBarneverntjeneste, expect no validation errors") {
			shouldNotThrowAny {
				val inner = "<OversendelseFylkesnemnd><Id>6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e</Id><DatoOversendtFylkesnemnd>$VALID_DATE</DatoOversendtFylkesnemnd><Lovhjemmel><Lov>BVL</Lov><Kapittel>1</Kapittel><Paragraf>2</Paragraf></Lovhjemmel></OversendelseFylkesnemnd>"
				getSchemaValidatorV5().validate(buildBarnevernXmlV5(inner).toStreamSource())
			}
		}

		forAll(
			row(
				"missing Id",
				"<OversendelseFylkesnemnd><DatoOversendtFylkesnemnd>$VALID_DATE</DatoOversendtFylkesnemnd><Lovhjemmel><Lov>BVL</Lov><Kapittel>1</Kapittel><Paragraf>2</Paragraf></Lovhjemmel></OversendelseFylkesnemnd>",
				"Id"
			),
			row(
				"empty Id",
				"<OversendelseFylkesnemnd><Id></Id><DatoOversendtFylkesnemnd>$VALID_DATE</DatoOversendtFylkesnemnd><Lovhjemmel><Lov>BVL</Lov><Kapittel>1</Kapittel><Paragraf>2</Paragraf></Lovhjemmel></OversendelseFylkesnemnd>",
				"UUID_ERROR"
			),
			row(
				"invalid Id",
				"<OversendelseFylkesnemnd><Id>42</Id><DatoOversendtFylkesnemnd>$VALID_DATE</DatoOversendtFylkesnemnd><Lovhjemmel><Lov>BVL</Lov><Kapittel>1</Kapittel><Paragraf>2</Paragraf></Lovhjemmel></OversendelseFylkesnemnd>",
				"UUID_ERROR"
			),
			row(
				"missing DatoOversendtFylkesnemnd",
				"<OversendelseFylkesnemnd><Id>6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e</Id><Lovhjemmel><Lov>BVL</Lov><Kapittel>1</Kapittel><Paragraf>2</Paragraf></Lovhjemmel></OversendelseFylkesnemnd>",
				"DatoOversendtFylkesnemnd"
			),
			row(
				"empty DatoOversendtFylkesnemnd",
				"<OversendelseFylkesnemnd><Id>6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e</Id><DatoOversendtFylkesnemnd></DatoOversendtFylkesnemnd><Lovhjemmel><Lov>BVL</Lov><Kapittel>1</Kapittel><Paragraf>2</Paragraf></Lovhjemmel></OversendelseFylkesnemnd>",
				EMPTY_DATE_ERROR
			),
			row(
				"invalid DatoOversendtFylkesnemnd",
				"<OversendelseFylkesnemnd><Id>6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e</Id><DatoOversendtFylkesnemnd>$INVALID_DATE</DatoOversendtFylkesnemnd><Lovhjemmel><Lov>BVL</Lov><Kapittel>1</Kapittel><Paragraf>2</Paragraf></Lovhjemmel></OversendelseFylkesnemnd>",
				INVALID_DATE_FORMAT_ERROR
			),
			row(
				"missing Lovhjemmel",
				"<OversendelseFylkesnemnd><Id>6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e</Id><DatoOversendtFylkesnemnd>$VALID_DATE</DatoOversendtFylkesnemnd></OversendelseFylkesnemnd>",
				"Lovhjemmel"
			)
		) { description, partialXml, expectedFragment ->
			When(description) {
				val thrown = shouldThrow<SAXException> {
					getSchemaValidatorV5().validate(buildBarnevernXmlV5(partialXml).toStreamSource())
				}

				Then("thrown should contain expected validation details") {
					val msg = thrown.message.orEmpty()
					if (expectedFragment == "UUID_ERROR") {
						msg shouldContain "not facet-valid"
						msg shouldContain "[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[1-5][0-9a-fA-F]{3}-[89abAB][0-9a-fA-F]{3}-[0-9a-fA-F]{12}"
					} else {
						msg shouldContain expectedFragment
					}
				}
			}
		}
	}
})

