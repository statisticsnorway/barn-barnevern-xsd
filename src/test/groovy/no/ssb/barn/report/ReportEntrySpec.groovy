package no.ssb.barn.report

import spock.lang.Specification

class ReportEntrySpec extends Specification {

    def "given valid ReportEntry, all props are set"() {
        given:
        def uuid = UUID.randomUUID()

        when:
        def sut = new ReportEntry(
                WarningLevel.WARNING,
                "~ruleName~",
                "~errorText~",
                "~type~",
                uuid)

        then:
        with(sut) {
            WarningLevel.WARNING == getWarningLevel()
            and:
            "~ruleName~" == getRuleName()
            and:
            "~errorText~" == getErrorText()
            and:
            "~type~" == getType()
            and:
            uuid == getId()
        }
    }
}
