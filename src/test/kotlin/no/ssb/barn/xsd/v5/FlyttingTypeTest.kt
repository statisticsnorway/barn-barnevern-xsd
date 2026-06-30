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

class FlyttingTypeTest : BehaviorSpec({
	Given("misc Flytting XML") {
		When("valid Flytting, expect no validation errors") {
			shouldNotThrowAny {
				val inner = "<Flytting><Id>6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e</Id><FlyttetDato>$VALID_DATE</FlyttetDato><ArsakFra>1.1.1</ArsakFra><FlyttingTil>1</FlyttingTil></Flytting>"
				getSchemaValidatorV5().validate(no.ssb.barn.TestUtils.buildBarnevernXmlV5(inner).toStreamSource())
			}
		}

		forAll(
			row(
				"missing Id",
				"<Flytting><FlyttetDato>$VALID_DATE</FlyttetDato><ArsakFra>1.1.1</ArsakFra><FlyttingTil>1</FlyttingTil></Flytting>",
				"Id"
			),
			row(
				"missing FlyttetDato",
				"<Flytting><Id>6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e</Id><ArsakFra>1.1.1</ArsakFra><FlyttingTil>1</FlyttingTil></Flytting>",
				"FlyttetDato"
			),
			row(
				"empty FlyttetDato",
				"<Flytting><Id>6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e</Id><FlyttetDato></FlyttetDato><ArsakFra>1.1.1</ArsakFra><FlyttingTil>1</FlyttingTil></Flytting>",
				EMPTY_DATE_ERROR
			),
			row(
				"invalid FlyttetDato",
				"<Flytting><Id>6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e</Id><FlyttetDato>$INVALID_DATE</FlyttetDato><ArsakFra>1.1.1</ArsakFra><FlyttingTil>1</FlyttingTil></Flytting>",
				INVALID_DATE_FORMAT_ERROR
			),
			row(
				"missing ArsakFra",
				"<Flytting><Id>6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e</Id><FlyttetDato>$VALID_DATE</FlyttetDato><FlyttingTil>1</FlyttingTil></Flytting>",
				"ArsakFra"
			),
			row(
				"missing FlyttingTil",
				"<Flytting><Id>6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e</Id><FlyttetDato>$VALID_DATE</FlyttetDato><ArsakFra>1.1.1</ArsakFra></Flytting>",
				"FlyttingTil"
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
