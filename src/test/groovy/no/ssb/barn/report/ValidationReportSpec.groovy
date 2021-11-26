package no.ssb.barn.report

import spock.lang.Specification

class ValidationReportSpec extends Specification {

    def "given valid ValidationReport, all props are set"() {
        when:
        def sut = new ValidationReport(
                "~messageId~",
                List.of(
                        new ReportEntry(
                                WarningLevel.WARNING,
                                "~ruleName~",
                                "~errorText~",
                                "~xmlContext~",
                                "~id~",
                                "~errorDetails~")
                ),
                WarningLevel.ERROR,
        )

        then:
        "~messageId~" == sut.getMessageId()
        and:
        1 == sut.getReportEntries().size()
        and:
        WarningLevel.ERROR == sut.severity
    }
}
