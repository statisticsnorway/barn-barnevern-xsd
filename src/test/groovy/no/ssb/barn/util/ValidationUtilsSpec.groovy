package no.ssb.barn.util

import org.xml.sax.SAXException
import spock.lang.Specification

import javax.xml.transform.stream.StreamSource

class ValidationUtilsSpec extends Specification {
    def "Should find sources from classpath, filename = #filename, result = #result"() {
        expect:
        (getSourceFromClasspath(filename) != null) == result

        where:
        filename            || result
        "/Barnevern.xsd"    || true
        "/test01_fil01.xml" || true
        "/test01_fil02.xml" || true
        "/invalid.xml"      || true
        "/nonExistentFile"  || false
    }

    def "Should validate valid xml files, xsd = #xsd, xml = #xml, result = #result"() {
        when:
        def sourceXSD = getSourceFromClasspath(xsd)
        def sourceXML = getSourceFromClasspath(xml)
        def validationResult = ValidationUtils.validateFromSources(sourceXSD, sourceXML)

        then:
        validationResult == result

        where:
        xsd              | xml                 || result
        "/Barnevern.xsd" | "/test01_fil01.xml" || true
        "/Barnevern.xsd" | "/test01_fil02.xml" || true
    }

    def "Should produce SAXException for invalid xml files, xsd = #xsd, xml = #xml"() {
        when:
        def sourceXSD = getSourceFromClasspath(xsd)
        def sourceXML = getSourceFromClasspath(xml)
        ValidationUtils.validateFromSources(sourceXSD, sourceXML)

        then:
        SAXException e = thrown()

        where:
        xsd              | xml
        "/Barnevern.xsd" | "/invalid.xml"
    }

    def "Should validate norwegian sosial security numbers (fnr), fnr = #fnr, result = #result"() {
        expect:
        ValidationUtils.validateSSN(fnr) == result

        where:
        fnr           | type  || result
        "05011399292" | "fnr" || true
        "41011088188" | "dnr" || true
        "01020304050" | "fnr" || false
        "ABCDEFGHIJK" | "???" || false
    }

    def getSourceFromClasspath(String path) {
        try {
            return new StreamSource(getClass().getResourceAsStream(path))
        } catch (Exception ignored) {
            return null
        }
    }
}
