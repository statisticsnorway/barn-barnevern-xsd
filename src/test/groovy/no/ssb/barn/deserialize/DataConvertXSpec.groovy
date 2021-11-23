package no.ssb.barn.deserialize

import no.ssb.barn.xsd.BarnevernType
import spock.lang.Ignore
import spock.lang.Specification

import javax.xml.transform.stream.StreamSource

class DataConvertXSpec extends Specification {

    @Ignore("Fix me")
    def "skriv ut data object fra xsd"() {
        given:
        def sourceXml = getSourceFromClasspath("./test01_fil01.xml")

        when:
        BarnevernType barnevernType = DataConvertX.unmarshallXML(sourceXml)

        then:
        barnevernType.toString()
    }

    @Ignore("Fix me")
    def "skriv ut data object as schema object"() {
        given:
        def xsdSource = getSourceFromClasspath("./Barnevern.xsd")
        and:
        def xmlSource = getSourceFromClasspath("./test01_fil01.xml")

        when:
        def barnevernType = DataConvertX.unmarshallXmlWithSchema(xsdSource, xmlSource)

        then:
        null != barnevernType
    }

    def getSourceFromClasspath(String path) {
        new StreamSource(this.getClass().getClassLoader()
                .getResourceAsStream(path))
    }
}
