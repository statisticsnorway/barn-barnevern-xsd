package no.ssb.barn.xsd.v5

import io.kotest.assertions.throwables.shouldNotThrowAny
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.data.forAll
import io.kotest.data.row
import io.kotest.matchers.string.shouldContain
import no.ssb.barn.TestUtils.VALID_DATE
import no.ssb.barn.TestUtils.VALID_DATO_UTTREKK
import no.ssb.barn.toStreamSource
import no.ssb.barn.util.ValidationUtils.getSchemaValidatorV5
import org.xml.sax.SAXException


class AvgiverTypeTest : BehaviorSpec({

    Given("misc Avgiver XML") {

        /** make sure it's possible to make a valid test XML */
        When("valid XML, expect no exceptions") {
            shouldNotThrowAny {
                getSchemaValidatorV5().validate(
                    buildXmlInTest(
                        "<Avgiver><Organisasjonsnummer>999999999</Organisasjonsnummer>" +
                            "<Kommunenummer>1234</Kommunenummer><Kommunenavn>~Kommunenavn~</Kommunenavn></Avgiver>"
                    ).toStreamSource()
                )
            }
        }

        forAll(
            row(
                "missing Organisasjonsnummer",
                "<Avgiver><Kommunenummer>1234</Kommunenummer><Kommunenavn>En kommune</Kommunenavn></Avgiver>",
                "One of '{Organisasjonsnummer}' is expected"
            ),
            row(
                "empty Organisasjonsnummer",
                "<Avgiver><Organisasjonsnummer></Organisasjonsnummer><Kommunenummer>1234</Kommunenummer><Kommunenavn>En kommune</Kommunenavn></Avgiver>",
                "not facet-valid"
            ),
            row(
                "invalid Organisasjonsnummer",
                "<Avgiver><Organisasjonsnummer>42</Organisasjonsnummer><Kommunenummer>1234</Kommunenummer><Kommunenavn>En kommune</Kommunenavn></Avgiver>",
                "not facet-valid"
            ),

            row(
                "missing Kommunenummer",
                "<Avgiver><Organisasjonsnummer>999999999</Organisasjonsnummer><Kommunenavn>En kommune</Kommunenavn></Avgiver>",
                "One of '{Kommunenummer}' is expected"
            ),
            row(
                "empty Kommunenummer",
                "<Avgiver><Organisasjonsnummer>999999999</Organisasjonsnummer><Kommunenummer></Kommunenummer><Kommunenavn>En kommune</Kommunenavn></Avgiver>",
                "not facet-valid"
            ),
            row(
                "invalid Kommunenummer",
                "<Avgiver><Organisasjonsnummer>999999999</Organisasjonsnummer><Kommunenummer>42</Kommunenummer><Kommunenavn>En kommune</Kommunenavn></Avgiver>",
                "not facet-valid"
            ),

            row(
                "missing Kommunenavn",
                "<Avgiver><Organisasjonsnummer>999999999</Organisasjonsnummer><Kommunenummer>1234</Kommunenummer></Avgiver>",
                "One of '{Kommunenavn}' is expected"
            ),
            row(
                "empty Kommunenavn",
                "<Avgiver><Organisasjonsnummer>999999999</Organisasjonsnummer><Kommunenummer>1234</Kommunenummer><Kommunenavn></Kommunenavn></Avgiver>",
                "minLength"
            ),
            row(
                "too long Kommunenavn",
                "<Avgiver><Organisasjonsnummer>999999999</Organisasjonsnummer><Kommunenummer>1234</Kommunenummer><Kommunenavn>" + "a".repeat(71) + "</Kommunenavn></Avgiver>",
                "maxLength"
            ),

            row(
                "empty Bydelsnummer",
                "<Avgiver><Organisasjonsnummer>999999999</Organisasjonsnummer><Kommunenummer>1234</Kommunenummer><Kommunenavn>En kommune</Kommunenavn><Bydelsnummer></Bydelsnummer><Bydelsnavn>Bydel</Bydelsnavn></Avgiver>",
                "not facet-valid"
            ),
            row(
                "too long Bydelsnummer",
                "<Avgiver><Organisasjonsnummer>999999999</Organisasjonsnummer><Kommunenummer>1234</Kommunenummer><Kommunenavn>En kommune</Kommunenavn><Bydelsnummer>111</Bydelsnummer><Bydelsnavn>Bydel</Bydelsnavn></Avgiver>",
                "not facet-valid"
            ),

            row(
                "empty Bydelsnavn",
                "<Avgiver><Organisasjonsnummer>999999999</Organisasjonsnummer><Kommunenummer>1234</Kommunenummer><Kommunenavn>En kommune</Kommunenavn><Bydelsnummer>11</Bydelsnummer><Bydelsnavn></Bydelsnavn></Avgiver>",
                "minLength"
            ),
            row(
                "too long Bydelsnavn",
                "<Avgiver><Organisasjonsnummer>999999999</Organisasjonsnummer><Kommunenummer>1234</Kommunenummer><Kommunenavn>En kommune</Kommunenavn><Bydelsnummer>11</Bydelsnummer><Bydelsnavn>" + "b".repeat(71) + "</Bydelsnavn></Avgiver>",
                "maxLength"
            )
        ) { description, partialXml, expectedErrorFragment ->
            When(description) {
                val thrown = shouldThrow<SAXException> {
                    getSchemaValidatorV5().validate(buildXmlInTest(partialXml).toStreamSource())
                }

                Then("thrown should contain expected validation details") {
                    thrown.message.orEmpty() shouldContain expectedErrorFragment
                }
            }
        }
    }

}) {
    companion object {
        private fun buildXmlInTest(avgiverXml: String): String =
            "<BarnevernregisterInnrapportering>" +
                "<Id>236110fc-edba-4b86-87b3-d6bb945cbc76</Id>" +
                "<DatoUttrekk>$VALID_DATO_UTTREKK</DatoUttrekk>" +
                "<Fagsystem><Leverandor>Netcompany</Leverandor><Navn>Modulus Barn</Navn><Versjon>1</Versjon></Fagsystem>" +
                avgiverXml +
                "<Sak><id>6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e</id><StartDato>$VALID_DATE</StartDato><Journalnummer>00004</Journalnummer></Sak>" +
                "</BarnevernregisterInnrapportering>"
    }
}