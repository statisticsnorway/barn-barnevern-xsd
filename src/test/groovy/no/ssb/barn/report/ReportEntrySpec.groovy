package no.ssb.barn.report

import spock.lang.Specification

class ReportEntrySpec extends Specification {

    def "given valid ReportEntry, all props are set"() {
        when:
        def sut = new ReportEntry(
                WarningLevel.WARNING,
                "~ruleName~",
                "~errorText~",
                "~xmlContext~",
                "~contextId~",
                "~errorDetails~")

        then:
        WarningLevel.WARNING == sut.getWarningLevel()
        and:
        "~ruleName~" == sut.getRuleName()
        and:
        "~errorText~" == sut.getErrorText()
        and:
        "~xmlContext~" == sut.getXmlContext()
        and:
        "~contextId~" == sut.getContextId()
        and:
        "~errorDetails~" == sut.getErrorDetails()
    }
}
