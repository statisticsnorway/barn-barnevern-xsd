package no.ssb.barn.validation

import org.xml.sax.SAXException
import spock.lang.Specification

import javax.xml.transform.stream.StreamSource

class ValidatorSpec extends Specification {
    def "Should find sources from classpath"() {
        when:
        def sourceFile = getSourceFromClasspath(filename)

        then:
        (sourceFile != null) == result

        where:
        filename            || result
        "/Barnevern.xsd"    || true
        "/test01_fil01.xml" || true
        "/test01_fil02.xml" || true
        "/invalid.xml"      || true
        "/nonExistentFile"  || false
    }

    def "Should validate valid xml files"() {
        when:
        def sourceXSD = getSourceFromClasspath(xsd)
        def sourceXML = getSourceFromClasspath(xml)
        def validationResult = Validator.validateFromSources(sourceXSD, sourceXML)

        then:
        validationResult == result

        where:
        xsd              | xml                 || result
        "/Barnevern.xsd" | "/test01_fil01.xml" || true
        "/Barnevern.xsd" | "/test01_fil02.xml" || true
    }

    def "Should produce SAXException for invalid xml files"() {
        when:
        def sourceXSD = getSourceFromClasspath(xsd)
        def sourceXML = getSourceFromClasspath(xml)
        Validator.validateFromSources(sourceXSD, sourceXML)

        then:
        SAXException e = thrown()

        where:
        xsd              | xml
        "/Barnevern.xsd" | "/invalid.xml"
    }

    def getSourceFromClasspath(String path) {
        try {
            return new StreamSource(getClass().getResourceAsStream(path))
        } catch (Exception e) {
            return null
        }
    }
}
