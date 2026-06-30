package no.ssb.barn.xsd.v5

import io.kotest.assertions.throwables.shouldNotThrowAny
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.string.shouldContain
import no.ssb.barn.TestUtils.VALID_DATE
import no.ssb.barn.toStreamSource
import no.ssb.barn.util.ValidationUtils.getSchemaValidatorV5
import org.xml.sax.SAXException

class HenvendelseTypeTest : BehaviorSpec({
	Given("misc Henvendelse XML") {
		When("valid Henvendelse, expect no validation errors") {
			shouldNotThrowAny {
				val inner = "<Henvendelse><Id>6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e</Id><StartDato>$VALID_DATE</StartDato></Henvendelse>"
				getSchemaValidatorV5().validate(no.ssb.barn.TestUtils.buildBarnevernXmlV5(inner).toStreamSource())
			}
		}

		When("missing StartDato in Henvendelse, expect validation error") {
			val inner = "<Henvendelse><Id>6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e</Id></Henvendelse>"
			val thrown = shouldThrow<SAXException> {
				getSchemaValidatorV5().validate(no.ssb.barn.TestUtils.buildBarnevernXmlV5(inner).toStreamSource())
			}

			Then("message should indicate missing StartDato") {
				thrown.message.orEmpty() shouldContain "One of '{StartDato}' is expected"
			}
		}
	}
})
