package no.ssb.barn.report

import spock.lang.Specification

class ValidationReportSpec extends Specification {

    def "given valid ValidationReport, all props are set"() {
        when:
        def sut = new ValidationReport(
                "~journalId~",
                "~individualId~",
                List.of(
                        new ReportEntry(
                                WarningLevel.WARNING,
                                "~ruleName~",
                                "~errorMessage~",
                                null)
                ))

        then:
        "~journalId~" == sut.getJournalId()
        and:
        "~individualId~" == sut.getIndividualId()
        and:
        1 == sut.getReportEntries().size()
    }
}
