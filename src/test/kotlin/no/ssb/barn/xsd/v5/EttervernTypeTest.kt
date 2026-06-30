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

class EttervernTypeTest : BehaviorSpec({
	Given("misc Ettervern XML") {
		When("valid Ettervern, expect no validation errors") {
			shouldNotThrowAny {
				val inner = "<Ettervern><Id>6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e</Id><TilbudSendtDato>$VALID_DATE</TilbudSendtDato></Ettervern>"
				getSchemaValidatorV5().validate(no.ssb.barn.TestUtils.buildBarnevernXmlV5(inner).toStreamSource())
			}
		}

		forAll(
			row(
				"duplicate Id",
				"<Ettervern><Id>6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e</Id><TilbudSendtDato>$VALID_DATE</TilbudSendtDato></Ettervern>" +
					"<Ettervern><Id>6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e</Id><TilbudSendtDato>$VALID_DATE</TilbudSendtDato></Ettervern>",
				"Duplicate unique value"
			),
			row(
				"missing Id",
				"<Ettervern><TilbudSendtDato>$VALID_DATE</TilbudSendtDato></Ettervern>",
				"Id"
			),
			row(
				"empty Id",
				"<Ettervern><Id></Id><TilbudSendtDato>$VALID_DATE</TilbudSendtDato></Ettervern>",
				"not facet-valid"
			),
			row(
				"invalid Id",
				"<Ettervern><Id>42</Id><TilbudSendtDato>$VALID_DATE</TilbudSendtDato></Ettervern>",
				"not facet-valid"
			),
			row(
				"empty MigrertId",
				"<Ettervern><Id>6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e</Id><MigrertId></MigrertId><TilbudSendtDato>$VALID_DATE</TilbudSendtDato></Ettervern>",
				"minLength"
			),
			row(
				"too long MigrertId",
				"<Ettervern><Id>6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e</Id><MigrertId>${"a".repeat(37)}</MigrertId><TilbudSendtDato>$VALID_DATE</TilbudSendtDato></Ettervern>",
				"maxLength"
			),
			row(
				"missing TilbudSendtDato",
				"<Ettervern><Id>6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e</Id></Ettervern>",
				"TilbudSendtDato"
			),
			row(
				"empty TilbudSendtDato",
				"<Ettervern><Id>6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e</Id><TilbudSendtDato></TilbudSendtDato></Ettervern>",
				EMPTY_DATE_ERROR
			),
			row(
				"invalid TilbudSendtDato",
				"<Ettervern><Id>6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e</Id><TilbudSendtDato>$INVALID_DATE</TilbudSendtDato></Ettervern>",
				INVALID_DATE_FORMAT_ERROR
			)
		) { description, partialXml, expectedFragment ->
			When(description) {
				val thrown = shouldThrow<SAXException> {
					getSchemaValidatorV5().validate(no.ssb.barn.TestUtils.buildBarnevernXmlV5(partialXml).toStreamSource())
				}

				Then("thrown should contain expected validation details") {
					thrown.message.orEmpty() shouldContain expectedFragment
				}
			}
		}
	}
})
