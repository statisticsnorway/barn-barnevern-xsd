package no.ssb.barn.xsd.v5

import io.kotest.assertions.throwables.shouldNotThrowAny
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.string.shouldContain
import no.ssb.barn.TestUtils.VALID_DATE
import no.ssb.barn.toStreamSource
import no.ssb.barn.util.ValidationUtils.getSchemaValidatorV5
import org.xml.sax.SAXException

class OppfolgingTypeTest : BehaviorSpec({
	Given("misc Oppfolging XML") {
		When("valid Oppfolging with Hyppighet, expect no validation errors") {
			shouldNotThrowAny {
				val hyppighet = "<Hyppighet><Id>6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e</Id><Dato>$VALID_DATE</Dato><HyppighetTidsenhet>1</HyppighetTidsenhet><HyppighetAntall>4</HyppighetAntall></Hyppighet>"
				val inner = "<Tiltak><Id>6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e</Id><StartDato>$VALID_DATE</StartDato><Lovhjemmel><Lov>BVL</Lov><Kapittel>1</Kapittel><Paragraf>2</Paragraf></Lovhjemmel><Kode>2.1</Kode><Oppfolging>$hyppighet</Oppfolging></Tiltak>"
				getSchemaValidatorV5().validate(no.ssb.barn.TestUtils.buildBarnevernXmlV5(inner).toStreamSource())
			}
		}

		When("missing Hyppighet in Oppfolging, expect validation error") {
			val inner = "<Tiltak><Id>6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e</Id><StartDato>$VALID_DATE</StartDato><Lovhjemmel><Lov>BVL</Lov><Kapittel>1</Kapittel><Paragraf>2</Paragraf></Lovhjemmel><Kode>2.1</Kode><Oppfolging></Oppfolging></Tiltak>"
			val thrown = shouldThrow<SAXException> {
				getSchemaValidatorV5().validate(no.ssb.barn.TestUtils.buildBarnevernXmlV5(inner).toStreamSource())
			}

			Then("message should indicate missing Hyppighet") {
				thrown.message.orEmpty() shouldContain "One of '{Hyppighet}' is expected"
			}
		}
	}
})

