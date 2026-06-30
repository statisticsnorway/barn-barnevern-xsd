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

class MelderTypeTest : BehaviorSpec({
	Given("misc Melder XML") {
		When("valid PrivatMelder, expect no validation errors") {
			shouldNotThrowAny {
				val inner = "<Henvendelse><Id>6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e</Id><StartDato>$VALID_DATE</StartDato><Melder><PrivatMelder>1</PrivatMelder></Melder></Henvendelse>"
				getSchemaValidatorV5().validate(buildBarnevernXmlV5(inner).toStreamSource())
			}
		}

		When("valid OffentligMelder, expect no validation errors") {
			shouldNotThrowAny {
				val inner = "<Henvendelse><Id>6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e</Id><StartDato>$VALID_DATE</StartDato><Melder><OffentligMelder>5</OffentligMelder></Melder></Henvendelse>"
				getSchemaValidatorV5().validate(buildBarnevernXmlV5(inner).toStreamSource())
			}
		}

		forAll(
			row(
				"empty Melder",
				"<Henvendelse><Id>6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e</Id><StartDato>$VALID_DATE</StartDato><Melder></Melder></Henvendelse>",
				"PrivatMelder"
			),
			row(
				"both Privat and Offentlig present",
				"<Henvendelse><Id>6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e</Id><StartDato>$VALID_DATE</StartDato><Melder><PrivatMelder>1</PrivatMelder><OffentligMelder>5</OffentligMelder></Melder></Henvendelse>",
				"OffentligMelder"
			),
			row(
				"invalid PrivatMelder code",
				"<Henvendelse><Id>6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e</Id><StartDato>$VALID_DATE</StartDato><Melder><PrivatMelder>42</PrivatMelder></Melder></Henvendelse>",
				"not facet-valid"
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
