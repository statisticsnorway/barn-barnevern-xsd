package no.ssb.barn.validation

import no.ssb.barn.report.WarningLevel
import spock.lang.Specification
import spock.lang.Subject

import static no.ssb.barn.testutil.TestDataProvider.getTestContextXmlOnly

class VersionOneValidatorSpec extends Specification {

    @Subject
    VersionOneValidator sut

    @SuppressWarnings('unused')
    def setup() {
        sut = new VersionOneValidator()
    }

    def "when validate with valid XML receive report without entries"() {
        given:
        def context = getTestContextXmlOnly("test01_fil01.xml")

        when:
        def report = sut.validate(context)

        then:
        0 == report.getReportEntries().size()
        and:
        WarningLevel.OK == report.severity
    }

    def "when message-level start date is before case-level start date, receive error"() {
        given:
        def context = getTestContextXmlOnly("sluttdato_etter_startdato.xml")

        when:
        def report = sut.validate(context)

        then:
        1 == report.reportEntries.size()
        and:
        WarningLevel.ERROR == report.severity
    }

    def "when validate with invalid XML receive report with one entry"() {
        given:
        def context = getTestContextXmlOnly("invalid.xml")

        when:
        def report = sut.validate(context)

        then:
        1 == report.reportEntries.size()
        and:
        report.reportEntries[0].warningLevel == WarningLevel.FATAL
        and:
        WarningLevel.FATAL == report.severity
    }
}
