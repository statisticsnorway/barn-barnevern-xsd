package no.ssb.barn.deserialize

import no.ssb.barn.xsd.BarnevernType
import spock.lang.Ignore
import spock.lang.Specification

import javax.xml.transform.Source
import javax.xml.transform.stream.StreamSource

class DataConvertXSpec extends Specification {

    def "skriv ut data object fra xsd"() {
        given:
            Source sourceXSD = getSourceFromClasspath("./Barnevern.xsd")

        when:
            BarnevernType convertObj = DataConvertX.unmarshallXML(sourceXSD)

        then:
            convertObj.toString()
    }

    @Ignore("Fix me")
    def "skriv ut data object as schema object"() {
        given:
        def sourceXSD = File.createTempFile("Barnevern", ".xsd")

        when:
        def convertObj = DataConvertX.unmarshallXMLwithSchema(sourceXSD)

        then:
        convertObj.toString()
    }

    def getSourceFromClasspath(String path) {
        try {
            return new StreamSource(getClass().getResourceAsStream(path))
        } catch (Exception ignored) {
            return null
        }
    }
}
