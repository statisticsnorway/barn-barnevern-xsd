package no.ssb.barn.validation

import no.ssb.barn.report.WarningLevel
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

    def "when validate with valid xml, receive validation report with OK"() {
        when:
        def validationReport = sut.validate(
                UUID.randomUUID().toString(),
                1,
                getResourceAsString("test01_fil01.xml"))

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
}
