package no.ssb.barn.util

import no.ssb.barn.generator.RandomUtils
import org.xml.sax.SAXException
import spock.lang.Specification
import spock.lang.Unroll

import javax.xml.transform.stream.StreamSource
import java.time.LocalDate

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

    def "Should validate norwegian social security numbers (fnr), fnr = #fnr, result = #result"() {
        expect:
        ValidationUtils.validateSSN(fnr) == result

        where:
        fnr           | type  || result
        "05011399292" | "fnr" || true
        "41011088188" | "dnr" || true
        "01020304050" | "fnr" || false
        "ABCDEFGHIJK" | "???" || false
    }

    @Unroll
    def "getAge all scenarios"() {
        expect:
        expectedAge == ValidationUtils.getAge(fnr)

        where:
        fnr             | type  || expectedAge
        "05011399292"   | "fnr" || 8
        "41011088188"   | "dnr" || -1
        "01020304050"   | "fnr" || 18
        "a".repeat(11)  | "???" || -1
        null            | "???" || -2
        getSsnByAge(99) | "fnr" || -1
    }

    static def getSsnByAge(int age) {
        RandomUtils.generateRandomSSN(
                LocalDate.now().minusYears(age + 1),
                LocalDate.now().minusYears(age)
        )
    }

    def getSourceFromClasspath(String path) {
        try {
            return new StreamSource(getClass().getResourceAsStream(path))
        } catch (Exception ignored) {
            return null
        }
    }
}
