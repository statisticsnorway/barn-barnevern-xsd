package no.ssb.barn.validation

import no.ssb.barn.report.WarningLevel
import spock.lang.Ignore
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Unroll

import static no.ssb.barn.testutil.TestDataProvider.getResourceAsString

class TheValidatorSpec extends Specification {

    @Shared
    TheValidator sut

    @SuppressWarnings('unused')
    def setupSpec() {
        sut = TheValidator.create()
    }

    def "when create, receive validator instance"() {
        expect:
        TheValidator.create() instanceof TheValidator
    }

    def "when validate with invalid xsd, receive exception"() {
        when:
        sut.validate(
                UUID.randomUUID().toString(), 2, "~xmlBody~")

        then:
        //noinspection GroovyUnusedAssignment
        IndexOutOfBoundsException e = thrown()
    }

    def "when validate with invalid xml, receive validation report with FATAL"() {
        when:
        def validationReport = sut.validate(
                UUID.randomUUID().toString(), 1, "~xmlBody~")

        then:
        WarningLevel.FATAL == validationReport.severity
    }

    @Ignore("Fix me")
    def "when validate with valid xml, receive validation report with OK"() {
        when:
        def validationReport = sut.validate(
                UUID.randomUUID().toString(),
                1,
                getResourceAsString("test01_file01_changes.xml"))

        then:
        WarningLevel.OK == validationReport.severity
    }

    @Unroll("test repeated #i time")
    def "when validate multiple times with invalid params, receive JSON result"() {
        when:
        def validationReport = sut.validate(
                UUID.randomUUID().toString(),
                1,
                getResourceAsString("barnevern_with_errors.xml"))

        then:
        noExceptionThrown()
        and:
        null != validationReport
        and:
        WarningLevel.FATAL == validationReport.severity

        where:
        i << (1..5)
    }

    def "when validating xml structure and invalid XSD version, receive fatal validation result"() {
        def invalidXsdVersion = -1
        when:
        def validationReport = sut.validateXmlStructure(
                UUID.randomUUID().toString(),
                invalidXsdVersion,
                getResourceAsString("barnevern_with_errors.xml"))

        then:
        noExceptionThrown()
        and:
        WarningLevel.FATAL == validationReport.severity
    }
    def "when validating xml structure and invalid xml, receive fatal validation result"() {
        when:
        def validationReport = sut.validateXmlStructure(
                UUID.randomUUID().toString(),
                1,
                getResourceAsString("invalid.xml"))

        then:
        noExceptionThrown()
        and:
        WarningLevel.FATAL == validationReport.severity
    }

    def "when validating xml structure and invalid rules, receive ok validation result"() {
        when:
        def validationReport = sut.validateXmlStructure(
                UUID.randomUUID().toString(),
                1,
                getResourceAsString("sluttdato_etter_startdato.xml"))

        then:
        noExceptionThrown()
        and:
        WarningLevel.OK == validationReport.severity
    }
}
