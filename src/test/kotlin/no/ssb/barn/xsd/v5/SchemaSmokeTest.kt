package no.ssb.barn.xsd.v5

import io.kotest.assertions.throwables.shouldNotThrowAny
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.string.shouldContain
import no.ssb.barn.TestUtils.INVALID_DATE
import no.ssb.barn.TestUtils.INVALID_DATE_FORMAT_ERROR
import no.ssb.barn.TestUtils.INVALID_DATO_UTTREKK
import no.ssb.barn.TestUtils.VALID_DATE
import no.ssb.barn.TestUtils.buildBarnevernXmlV5
import no.ssb.barn.toStreamSource
import no.ssb.barn.util.ValidationUtils.getSchemaValidatorV5
import org.xml.sax.SAXException

class SchemaSmokeTest : BehaviorSpec({
    Given("a small smoke suite for Barnevern v5 XSD") {
        When("a canonical valid document is validated") {
            shouldNotThrowAny {
                val inner =
                    "<Tiltak><Id>6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e</Id><StartDato>$VALID_DATE</StartDato><Lovhjemmel><Lov>BVL</Lov><Kapittel>1</Kapittel><Paragraf>2</Paragraf></Lovhjemmel><Kode>2.1</Kode></Tiltak>" +
                            "<Vedtak><Id>6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e</Id><StartDato>$VALID_DATE</StartDato><Lovhjemmel><Lov>BVL</Lov><Kapittel>1</Kapittel><Paragraf>2</Paragraf></Lovhjemmel><Krav><Id>6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e</Id><DatoKravMottatt>$VALID_DATE</DatoKravMottatt></Krav><Status><Id>6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e</Id><EndretDato>$VALID_DATE</EndretDato><Kode>1</Kode></Status></Vedtak>"

                getSchemaValidatorV5().validate(buildBarnevernXmlV5(inner).toStreamSource())
            }
        }

        When("an element has an empty UUID, expect pattern validation error") {
            val inner = "<Tiltak><Id></Id><StartDato>$VALID_DATE</StartDato><Lovhjemmel><Lov>BVL</Lov><Kapittel>1</Kapittel><Paragraf>2</Paragraf></Lovhjemmel><Kode>2.1</Kode></Tiltak>"
            val thrown = shouldThrow<SAXException> {
                getSchemaValidatorV5().validate(buildBarnevernXmlV5(inner).toStreamSource())
            }

            Then("message should indicate facet/pattern failure") {
                val msg = thrown.message.orEmpty()
                msg shouldContain "not facet-valid"
                msg shouldContain "[0-9a-fA-F]{8}"
            }
        }

        When("duplicate IDs are present, expect identity constraint failure") {
            val id = "6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e"
            val inner = "<Tiltak><Id>$id</Id><StartDato>$VALID_DATE</StartDato><Lovhjemmel><Lov>BVL</Lov><Kapittel>1</Kapittel><Paragraf>2</Paragraf></Lovhjemmel><Kode>2.1</Kode></Tiltak>" +
                    "<Tiltak><Id>$id</Id><StartDato>$VALID_DATE</StartDato><Lovhjemmel><Lov>BVL</Lov><Kapittel>1</Kapittel><Paragraf>2</Paragraf></Lovhjemmel><Kode>2.1</Kode></Tiltak>"

            val thrown = shouldThrow<SAXException> {
                getSchemaValidatorV5().validate(buildBarnevernXmlV5(inner).toStreamSource())
            }

            Then("message should indicate duplicate identity constraint") {
                thrown.message.orEmpty() shouldContain "Duplicate unique value"
            }
        }

        When("date fields have invalid format, expect datatype validation error") {
            val inner = "<Tiltak><Id>6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e</Id><StartDato>$INVALID_DATE</StartDato><Lovhjemmel><Lov>BVL</Lov><Kapittel>1</Kapittel><Paragraf>2</Paragraf></Lovhjemmel><Kode>2.1</Kode></Tiltak>"
            val thrown = shouldThrow<SAXException> {
                getSchemaValidatorV5().validate(buildBarnevernXmlV5(inner).toStreamSource())
            }

            Then("message should contain date error details") {
                thrown.message.orEmpty() shouldContain INVALID_DATE_FORMAT_ERROR
                thrown.message.orEmpty() shouldContain "date"
            }
        }

        When("datoUttrekk has invalid format, expect datatype validation error") {
            val inner = "<Tiltak><Id>6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e</Id><StartDato>$VALID_DATE</StartDato><Lovhjemmel><Lov>BVL</Lov><Kapittel>1</Kapittel><Paragraf>2</Paragraf></Lovhjemmel><Kode>2.1</Kode></Tiltak>"
            val thrown = shouldThrow<SAXException> {
                getSchemaValidatorV5().validate(
                    buildBarnevernXmlV5(datoUttrekk = INVALID_DATO_UTTREKK, innerXml = inner).toStreamSource()
                )
            }

            Then("message should contain dateTime error details") {
                thrown.message.orEmpty() shouldContain INVALID_DATO_UTTREKK
                thrown.message.orEmpty() shouldContain "DatoTidUttrekk"
            }
        }

        When("Henvendelse has MelderType with both OffentligMelder and PrivatMelder") {
            val inner = "<Henvendelse>" +
                    "<Id>6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e</Id>" +
                    "<StartDato>$VALID_DATE</StartDato>" +
                    "<Melder>" +
                        "<OffentligMelder>5</OffentligMelder>" +
                        "<PrivatMelder>1</PrivatMelder>" +
                    "</Melder>" +
                "</Henvendelse>"
            val thrown = shouldThrow<SAXException> {
                getSchemaValidatorV5().validate(buildBarnevernXmlV5(inner).toStreamSource())
            }

            Then("message should contain error") {
                thrown.message.orEmpty() shouldContain "No child element is expected at this point"
            }
        }

        When("Henvendelse has MelderType with only OffentligMelder") {
            shouldNotThrowAny {
                val inner = "<Henvendelse>" +
                    "<Id>6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e</Id>" +
                    "<StartDato>$VALID_DATE</StartDato>" +
                    "<Melder>" +
                    "<OffentligMelder>5</OffentligMelder>" +
                    "</Melder>" +
                    "</Henvendelse>"

                getSchemaValidatorV5().validate(buildBarnevernXmlV5(inner).toStreamSource())
            }
        }
    }
})