package no.ssb.barn.report

import spock.lang.Specification

class ValidationReportSpec extends Specification {

    def "given valid ValidationReport, all props are set"() {
        given:
        def uuid = UUID.randomUUID()

        when:
        def sut = new ValidationReport(
                "~messageId~",
                List.of(
                        new ReportEntry(
                                WarningLevel.WARNING,
                                "~ruleName~",
                                "~errorText~",
                                "~xmlContext~",
                                uuid)
                ),
                WarningLevel.ERROR,
        )

        then:
        with(sut) {
            "~messageId~" == getMessageId()
            and:
            1 == getReportEntries().size()
            and:
            WarningLevel.ERROR == severity
        }
    }
}
