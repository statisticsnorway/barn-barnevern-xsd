package no.ssb.barn.xsd.v5

import io.kotest.assertions.throwables.shouldNotThrowAny
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.string.shouldContain
import no.ssb.barn.TestUtils.VALID_DATE
import no.ssb.barn.TestUtils.buildBarnevernXmlV5
import no.ssb.barn.toStreamSource
import no.ssb.barn.util.ValidationUtils.getSchemaValidatorV5
import org.xml.sax.SAXException

class OversendelsePrivatKravTypeTest : BehaviorSpec({
	Given("misc OversendelsePrivatKrav XML") {
		When("valid OversendelsePrivatKrav, expect no validation errors") {
			shouldNotThrowAny {
				val krav = "<Krav><Id>6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e</Id><DatoKravMottatt>$VALID_DATE</DatoKravMottatt></Krav>"
				getSchemaValidatorV5().validate(buildBarnevernXmlV5(buildXml(krav)).toStreamSource())
			}
		}

		When("missing DatoKravMottatt, expect validation error") {
			val krav = "<Krav><Id>6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e</Id></Krav>"
			val thrown = shouldThrow<SAXException> {
				getSchemaValidatorV5().validate(buildBarnevernXmlV5(buildXml(krav)).toStreamSource())
			}

			Then("message should indicate missing DatoKravMottatt") {
				thrown.message.orEmpty() shouldContain "DatoKravMottatt"
			}
		}

		When("empty Krav Id, expect pattern validation error") {
			val krav = "<Krav><Id></Id><DatoKravMottatt>$VALID_DATE</DatoKravMottatt></Krav>"
			val thrown = shouldThrow<SAXException> {
				getSchemaValidatorV5().validate(buildBarnevernXmlV5(buildXml(krav)).toStreamSource())
			}

			Then("message should indicate UUID pattern failure") {
				val msg = thrown.message.orEmpty()
				msg shouldContain "not facet-valid"
				msg shouldContain "[0-9a-fA-F]{8}"
			}
		}

		When("invalid Krav Id, expect pattern validation error") {
			val krav = "<Krav><Id>42</Id><DatoKravMottatt>$VALID_DATE</DatoKravMottatt></Krav>"
			val thrown = shouldThrow<SAXException> {
				getSchemaValidatorV5().validate(buildBarnevernXmlV5(buildXml(krav)).toStreamSource())
			}

			Then("message should indicate UUID pattern failure") {
				val msg = thrown.message.orEmpty()
				msg shouldContain "not facet-valid"
				msg shouldContain "[0-9a-fA-F]{8}"
			}
		}
	}
}) {
	companion object {
		// small helper to build a valid <Vedtak> wrapper for tests (adds required Status)
		private fun buildXml(inner: String) =
			"<Vedtak><Id>6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e</Id><StartDato>$VALID_DATE</StartDato><Lovhjemmel><Lov>BVL</Lov><Kapittel>1</Kapittel><Paragraf>2</Paragraf></Lovhjemmel>" +
					inner +
					"<Status><Id>6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e</Id><EndretDato>$VALID_DATE</EndretDato><Kode>1</Kode></Status></Vedtak>"
	}
}
