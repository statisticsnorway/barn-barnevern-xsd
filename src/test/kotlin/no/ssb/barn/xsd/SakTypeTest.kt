package no.ssb.barn.xsd

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.data.forAll
import io.kotest.data.row
import io.kotest.matchers.string.shouldStartWith
import no.ssb.barn.util.ValidationUtils.getSchemaValidator
import org.xml.sax.SAXException
import java.io.StringReader
import javax.xml.transform.stream.StreamSource

class SakTypeTest : BehaviorSpec({

    given("misc invalid Sak start tags") {

        forAll(
            row(
                "missing Id",
                "<Sak StartDato=\"2022-11-14\" Journalnummer=\"2022-00004\">"
            ),
            row(
                "empty Id",
                "<Sak Id=\"\" StartDato=\"2022-11-14\" Journalnummer=\"2022-00004\">"
            ),
            row(
                "invalid Id",
                "<Sak Id=\"42\" StartDato=\"2022-11-14\" Journalnummer=\"2022-00004\">"
            ),
            row(
                "too long Id",
                "<Sak Id=\"${"a".repeat(37)}\" StartDato=\"2022-11-14\" Journalnummer=\"2022-00004\">"
            ),

            row(
                "empty MigrertId",
                "<Sak Id=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" MigrertId=\"\" StartDato=\"2022-11-14\" Journalnummer=\"2022-00004\">"
            ),
            row(
                "too long MigrertId",
                "<Sak Id=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" MigrertId=\"${"a".repeat(37)}\" StartDato=\"2022-11-14\" Journalnummer=\"2022-00004\">"
            )
        ) { description, sakStartTag ->
            `when`(description) {
                val thrown = shouldThrow<SAXException> {
                    getSchemaValidator().validate(StreamSource(StringReader(buildXmlInTest(sakStartTag))))
                }

                then("thrown should be as expected") {
                    thrown.message shouldStartWith "cvc-"
                }
            }
        }
    }
}) {
    companion object {
        // <Sak Id=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" StartDato=\"2022-11-14\" Journalnummer=\"2022-00004\">"
        fun buildXmlInTest(sakStartTag: String): String =
            "<Barnevern Id=\"236110fc-edba-4b86-87b3-d6bb945cbc76\" DatoUttrekk=\"2022-11-14T15:13:33.1077852+01:00\">" +
                    "<Fagsystem Leverandor=\"Netcompany\" Navn=\"Modulus Barn\" Versjon=\"1\" />" +
                    "<Avgiver Organisasjonsnummer=\"\" Kommunenummer=\"1234\" Kommunenavn=\"En kommune\" />" +
                    sakStartTag +
                    "<Melding Id=\"e16bec92-70fe-4313-957b-81430aced812\" StartDato=\"2022-11-14\">" +
                    "<Melder Kode=\"\" /></Melding></Sak></Barnevern>"
    }
}
