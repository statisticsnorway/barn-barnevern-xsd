package no.ssb.barn

import java.io.StringReader
import javax.xml.transform.stream.StreamSource


fun String.toStreamSource() = StreamSource(StringReader(this))


object TestUtils {
    fun getResourceAsString(resourceName: String) = this.javaClass.getResource("/$resourceName")!!.readText()

    const val LOVHJEMMEL_XML = "<Lovhjemmel><Lov>BVL</Lov><Kapittel>1</Kapittel><Paragraf>2</Paragraf></Lovhjemmel>"

    fun buildBarnevernXml(innerXml: String) =
        "<Barnevern Id=\"236110fc-edba-4b86-87b3-d6bb945cbc76\" DatoUttrekk=\"2022-11-14T15:13:33.1077852+01:00\">" +
                "<Fagsystem Leverandor=\"Netcompany\" Navn=\"Modulus Barn\" Versjon=\"1\"/>" +
                "<Avgiver Organisasjonsnummer=\"111111111\" Kommunenummer=\"1234\" Kommunenavn=\"~Kommunenavn~\"/>" +
                "<Sak Id=\"6ee9bf92-7a4e-46ef-a2dd-b5a3a0a9ee2e\" StartDato=\"2022-11-14\" Journalnummer=\"2022-00004\">" +
                innerXml +
                "</Sak></Barnevern>"
}
