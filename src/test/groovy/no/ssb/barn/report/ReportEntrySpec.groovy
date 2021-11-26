package no.ssb.barn.report

import spock.lang.Specification

class ReportEntrySpec extends Specification {

    def "given valid ReportEntry, all props are set"() {
        when:
        def sut = new ReportEntry(
                WarningLevel.WARNING,
                "~ruleName~",
                "~errorText~",
                "~type~",
                "~id~",
                "~errorDetails~")

        then:
        WarningLevel.WARNING == sut.getWarningLevel()
        and:
        "~ruleName~" == sut.getRuleName()
        and:
        "~errorText~" == sut.getErrorText()
        and:
        "~type~" == sut.getType()
        and:
        "~id~" == sut.getId()
        and:
        "~errorDetails~" == sut.getErrorDetails()
    }
}
