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

class PersonaliaTypeTest : BehaviorSpec({
	Given("misc Personalia XML") {
		When("valid Personalia, expect no validation errors") {
			shouldNotThrowAny {
				val inner = "<Personalia><Id>6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e</Id><StartDato>$VALID_DATE</StartDato><Fodselsdato>$VALID_DATE</Fodselsdato></Personalia>"
				getSchemaValidatorV5().validate(buildBarnevernXmlV5(inner).toStreamSource())
			}
		}

		forAll(
			row(
				"missing Id",
				"<Personalia><StartDato>$VALID_DATE</StartDato></Personalia>",
				"Id"
			),
			row(
				"empty Id",
				"<Personalia><Id></Id><StartDato>$VALID_DATE</StartDato></Personalia>",
				"not facet-valid"
			),
			row(
				"missing StartDato",
				"<Personalia><Id>6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e</Id></Personalia>",
				"StartDato"
			),
			row(
				"empty Fodselsdato",
				"<Personalia><Id>6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e</Id><StartDato>$VALID_DATE</StartDato><Fodselsdato></Fodselsdato></Personalia>",
				EMPTY_DATE_ERROR
			),
			row(
				"invalid Fodselsdato",
				"<Personalia><Id>6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e</Id><StartDato>$VALID_DATE</StartDato><Fodselsdato>$INVALID_DATE</Fodselsdato></Personalia>",
				INVALID_DATE_FORMAT_ERROR
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

