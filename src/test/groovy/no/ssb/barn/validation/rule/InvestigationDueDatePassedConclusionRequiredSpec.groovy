package no.ssb.barn.validation.rule

import no.ssb.barn.report.WarningLevel
import no.ssb.barn.validation.ValidationContext
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

import java.time.ZonedDateTime

import static no.ssb.barn.testutil.TestDataProvider.getTestContext

class InvestigationDueDatePassedConclusionRequiredSpec extends Specification {

    @Subject
    InvestigationDueDatePassedConclusionRequired sut

    ValidationContext context

    @SuppressWarnings('unused')
    def setup() {
        sut = new InvestigationDueDatePassedConclusionRequired()
        context = getTestContext()
    }

    @Unroll
    def "test av alle scenarier"() {
        given:
        def investigation = context.rootObject.sak.undersokelse[0]
        and:
        investigation.startDato = startDate
        and:
        if (resetConclusion) {
            investigation.konklusjon = null
        }

        when:
        def reportEntries = sut.validate(context)

        then:
        noExceptionThrown()
        and:
        (reportEntries != null) == errorExpected
        and:
        if (errorExpected) {
            assert 1 == reportEntries.size()
            assert WarningLevel.WARNING == reportEntries[0].warningLevel
            assert reportEntries[0].errorText.contains("og skal konkluderes da den har pågått i mer enn 6 måneder")
        }

        where:
        resetConclusion | startDate   || errorExpected
        true            | getDate(-7) || true
        true            | getDate(-5) || false
        false           | getDate(-5) || false
        false           | getDate(-6) || false
        false           | getDate(-7) || false
    }

    static def getDate(months) {
        ZonedDateTime.now().plusMonths(months)
    }
}
