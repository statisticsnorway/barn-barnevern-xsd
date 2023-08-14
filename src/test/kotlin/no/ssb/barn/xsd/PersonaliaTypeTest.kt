package no.ssb.barn.xsd

import io.kotest.assertions.throwables.shouldNotThrowAny
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.data.forAll
import io.kotest.data.row
import io.kotest.matchers.shouldBe
import no.ssb.barn.TestUtils.EMPTY_DATE_ERROR
import no.ssb.barn.TestUtils.EMPTY_ID_ERROR
import no.ssb.barn.TestUtils.INVALID_DATE
import no.ssb.barn.TestUtils.INVALID_DATE_FORMAT_ERROR
import no.ssb.barn.TestUtils.INVALID_ID_ERROR
import no.ssb.barn.TestUtils.INVALID_MAX_DATE_TOO_LATE
import no.ssb.barn.TestUtils.INVALID_MIN_DATE_TOO_EARLY
import no.ssb.barn.TestUtils.START_DATE_TOO_EARLY_ERROR
import no.ssb.barn.TestUtils.START_DATE_TOO_LATE_ERROR
import no.ssb.barn.TestUtils.VALID_DATE
import no.ssb.barn.TestUtils.buildBarnevernXml
import no.ssb.barn.toStreamSource
import no.ssb.barn.util.ValidationUtils.getSchemaValidator
import org.xml.sax.SAXException

class PersonaliaTypeTest : BehaviorSpec({

    Given("misc Personalia XML") {

        /** make sure it's possible to make a valid test XML */
        When("valid XML, expect no exceptions") {
            shouldNotThrowAny {
                getSchemaValidator().validate(
                    buildBarnevernXml(
                        "<Personalia Id=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" " +
                                "StartDato=\"$VALID_DATE\" " +
                                "Fodselsnummer=\"01012199999\" " +
                                "Fodseldato=\"2021-01-01\" " +
                                "Kjonn=\"1\" " +
                                "DUFnummer=\"${"1".repeat(12)}\" " +
                                "/>"
                    ).toStreamSource()
                )
            }
        }

        forAll(
            /** Id */

            row(
                "duplicate Id",
                "<Personalia Id=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" StartDato=\"$VALID_DATE\" "+
                        "Fodselsnummer=\"01012199999\" Fodseldato=\"2021-01-01\" Kjonn=\"1\" />" +
                        "<Personalia Id=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" StartDato=\"$VALID_DATE\" "+
                        "Fodselsnummer=\"01012199999\" Fodseldato=\"2021-01-01\" Kjonn=\"1\" />",
                "cvc-identity-constraint.4.1: Duplicate unique value [6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e] " +
                        "declared for identity constraint \"PersonaliaIdUnique\" of element \"Sak\"."
            ),

            row(
                "missing Id",
                "<Personalia StartDato=\"$VALID_DATE\" Fodselsnummer=\"01012199999\" " +
                        "Fodseldato=\"2021-01-01\" Kjonn=\"1\" />",
                "cvc-complex-type.4: Attribute 'Id' must appear on element 'Personalia'."
            ),
            row(
                "empty Id",
                "<Personalia Id=\"\" StartDato=\"$VALID_DATE\" Fodselsnummer=\"01012199999\" " +
                        "Fodseldato=\"2021-01-01\" Kjonn=\"1\" />",
                EMPTY_ID_ERROR
            ),
            row(
                "invalid Id",
                "<Personalia Id=\"42\" StartDato=\"$VALID_DATE\" Fodselsnummer=\"01012199999\" " +
                        "Fodseldato=\"2021-01-01\" Kjonn=\"1\" />",
                INVALID_ID_ERROR
            ),

            /** StartDato */
            row(
                "missing StartDato",
                "<Personalia Id=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" Fodselsnummer=\"01012199999\" " +
                        "Fodseldato=\"2021-01-01\" Kjonn=\"1\"/>",
                "cvc-complex-type.4: Attribute 'StartDato' must appear on element 'Personalia'."
            ),
            row(
                "empty StartDato",
                "<Personalia Id=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" StartDato=\"\" " +
                        "Fodselsnummer=\"01012199999\" Fodseldato=\"2021-01-01\" Kjonn=\"1\"/>",
                EMPTY_DATE_ERROR
            ),
            row(
                "invalid StartDato",
                "<Personalia Id=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" StartDato=\"$INVALID_DATE\" " +
                        "Fodselsnummer=\"01012199999\" Fodseldato=\"2021-01-01\" Kjonn=\"1\"/>",
                INVALID_DATE_FORMAT_ERROR
            ),
            row(
                "StartDato too early",
                "<Personalia Id=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" StartDato=\"$INVALID_MIN_DATE_TOO_EARLY\" " +
                        "Fodselsnummer=\"01012199999\" Fodseldato=\"2021-01-01\" Kjonn=\"1\"/>",
                START_DATE_TOO_EARLY_ERROR
            ),
            row(
                "StartDato too late",
                "<Personalia Id=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" StartDato=\"$INVALID_MAX_DATE_TOO_LATE\" " +
                        "Fodselsnummer=\"01012199999\" Fodseldato=\"2021-01-01\" Kjonn=\"1\"/>",
                START_DATE_TOO_LATE_ERROR
            ),

            /** Fodselsnummer */
            row(
                "missing Fodselsnummer",
                "<Personalia Id=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" StartDato=\"$VALID_DATE\" " +
                        "Fodseldato=\"2021-01-01\" Kjonn=\"1\" />",
                "cvc-complex-type.4: Attribute 'Fodselsnummer' must appear on element 'Personalia'."
            ),
            row(
                "empty Fodselsnummer",
                "<Personalia Id=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" StartDato=\"$VALID_DATE\" " +
                        "Fodselsnummer=\"\" Fodseldato=\"2021-01-01\" Kjonn=\"1\" />",
                "cvc-pattern-valid: Value '' is not facet-valid with respect to pattern '\\d{11}' for " +
                        "type '#AnonType_FodselsnummerPersonaliaSakType'."
            ),
            row(
                "invalid Fodselsnummer",
                "<Personalia Id=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" StartDato=\"$VALID_DATE\" " +
                        "Fodselsnummer=\"42\" Fodseldato=\"2021-01-01\" Kjonn=\"1\" />",
                "cvc-pattern-valid: Value '42' is not facet-valid with respect to pattern '\\d{11}' for " +
                        "type '#AnonType_FodselsnummerPersonaliaSakType'."
            ),

            /** Fodseldato */
            row(
                "missing Fodseldato",
                "<Personalia Id=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" StartDato=\"$VALID_DATE\" " +
                        "Fodselsnummer=\"01012199999\" Kjonn=\"1\" />",
                "cvc-complex-type.4: Attribute 'Fodseldato' must appear on element 'Personalia'."
            ),
            row(
                "empty Fodseldato",
                "<Personalia Id=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" StartDato=\"$VALID_DATE\" " +
                        "Fodselsnummer=\"01012199999\" Fodseldato=\"\" Kjonn=\"1\" />",
                EMPTY_DATE_ERROR
            ),
            row(
                "invalid Fodseldato",
                "<Personalia Id=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" StartDato=\"$VALID_DATE\" " +
                        "Fodselsnummer=\"01012199999\" Fodseldato=\"2021\" Kjonn=\"1\" />",
                "cvc-datatype-valid.1.2.1: '2021' is not a valid value for 'date'."
            ),

            /** Kjonn */
            row(
                "missing Kjonn",
                "<Personalia Id=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" StartDato=\"$VALID_DATE\" " +
                        "Fodselsnummer=\"01012199999\" Fodseldato=\"2021-01-01\" />",
                "cvc-complex-type.4: Attribute 'Kjonn' must appear on element 'Personalia'."
            ),
            row(
                "empty Kjonn",
                "<Personalia Id=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" StartDato=\"$VALID_DATE\" " +
                        "Fodselsnummer=\"01012199999\" Fodseldato=\"2021-01-01\" Kjonn=\"\" />",
                "cvc-length-valid: Value '' with length = '0' is not facet-valid with respect to length '1' for " +
                        "type '#AnonType_KjonnPersonaliaSakType'."
            ),
            row(
                "invalid Kjonn",
                "<Personalia Id=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" StartDato=\"$VALID_DATE\" " +
                        "Fodselsnummer=\"01012199999\" Fodseldato=\"2021-01-01\" Kjonn=\"4\" />",
                "cvc-enumeration-valid: Value '4' is not facet-valid with respect to enumeration '[0, 1, 2]'. " +
                        "It must be a value from the enumeration."
            ),

            /** DUFnummer */
            row(
                "empty DUFnummer",
                "<Personalia Id=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" StartDato=\"$VALID_DATE\" " +
                        "Fodselsnummer=\"01012199999\" Fodseldato=\"2021-01-01\" Kjonn=\"1\" DUFnummer=\"\" />",
                "cvc-pattern-valid: Value '' is not facet-valid with respect to pattern '\\d{12}' for type " +
                        "'#AnonType_DUFnummerPersonaliaSakType'."
            ),
            row(
                "invalid DUFnummer",
                "<Personalia Id=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" StartDato=\"$VALID_DATE\" " +
                        "Fodselsnummer=\"01012199999\" Fodseldato=\"2021-01-01\" Kjonn=\"1\" DUFnummer=\"42\" />",
                "cvc-pattern-valid: Value '42' is not facet-valid with respect to pattern '\\d{12}' for type " +
                        "'#AnonType_DUFnummerPersonaliaSakType'."
            )
        ) { description, partialXml, expectedError ->
            When(description) {
                val thrown = shouldThrow<SAXException> {
                    getSchemaValidator().validate(buildBarnevernXml(partialXml).toStreamSource())
                }

                Then("thrown should be as expected") {
                    thrown.message shouldBe expectedError
                }
            }
        }
    }
})