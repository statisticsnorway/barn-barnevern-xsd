package no.ssb.barn.xsd.v5

import io.kotest.assertions.throwables.shouldNotThrowAny
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.data.forAll
import io.kotest.data.row
import io.kotest.matchers.string.shouldContain
import no.ssb.barn.TestUtils.VALID_DATE
import no.ssb.barn.toStreamSource
import no.ssb.barn.util.ValidationUtils.getSchemaValidatorV5
import org.xml.sax.SAXException

class BarnevernregisterInnrapporteringTypeTest : BehaviorSpec({

    Given("misc Barnevern XML") {

        forAll(
            row("zone offset = +01:00", "2023-11-14T15:13:33+01:00"),
            row("zone offset = Z", "2023-11-14T15:13:33Z"),
            row("fractional and zone offset = +01:00", "2023-11-14T15:13:33.123456789+01:00"),
            row("fractional and zone offset = Z", "2023-11-14T15:13:33.123456789Z"),
        ) { description, dateTimeString ->
            When(description) {
                shouldNotThrowAny {
                    getSchemaValidatorV5().validate(
                        buildXmlInTest(
                            "<Id>236110fc-edba-4b86-87b3-d6bb945cbc76</Id>" +
                                    "<DatoUttrekk>$dateTimeString</DatoUttrekk>"
                        ).toStreamSource()
                    )
                }
            }
        }

        forAll(
            row(
                "missing Id",
                "<DatoUttrekk>2023-11-14T15:13:33+01:00</DatoUttrekk>",
                "One of '{Id}' is expected"
            ),
            row(
                "empty Id",
                "<Id></Id><DatoUttrekk>2023-11-14T15:13:33+01:00</DatoUttrekk>",
                "Value '' is not facet-valid"
            ),
            row(
                "invalid Id",
                "<Id>42</Id><DatoUttrekk>2023-11-14T15:13:33+01:00</DatoUttrekk>",
                "Value '42' is not facet-valid"
            ),
            row(
                "missing DatoUttrekk",
                "<Id>236110fc-edba-4b86-87b3-d6bb945cbc76</Id>",
                "One of '{DatoUttrekk}' is expected"
            ),
            row(
                "empty DatoUttrekk",
                "<Id>236110fc-edba-4b86-87b3-d6bb945cbc76</Id><DatoUttrekk></DatoUttrekk>",
                "'' is not a valid value for 'dateTime'"
            ),
            row(
                "invalid DatoUttrekk",
                "<Id>236110fc-edba-4b86-87b3-d6bb945cbc76</Id><DatoUttrekk>2023</DatoUttrekk>",
                "'2023' is not a valid value for 'dateTime'"
            )
        ) { description, rootElements, expectedErrorFragment ->
            When(description) {
                val thrown = shouldThrow<SAXException> {
                    getSchemaValidatorV5().validate(buildXmlInTest(rootElements).toStreamSource())
                }

                Then("thrown should contain expected validation details") {
                    thrown.message.orEmpty() shouldContain expectedErrorFragment
                }
            }
        }
    }
}) {
    companion object {
        private fun buildXmlInTest(rootStartElements: String): String =
            "<BarnevernregisterInnrapportering>" +
                    rootStartElements +
                    "<Fagsystem><Leverandor>Netcompany</Leverandor><Navn>Modulus Barn</Navn><Versjon>1</Versjon></Fagsystem>" +
                    "<Avgiver><Organisasjonsnummer>999999999</Organisasjonsnummer><Kommunenummer>1234</Kommunenummer><Kommunenavn>En kommune</Kommunenavn></Avgiver>" +
                    "<Sak><id>6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e</id><StartDato>$VALID_DATE</StartDato><Journalnummer>00004</Journalnummer></Sak>" +
                    "</BarnevernregisterInnrapportering>"
    }
}
