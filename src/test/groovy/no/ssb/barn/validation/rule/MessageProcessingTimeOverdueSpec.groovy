package no.ssb.barn.validation.rule

import no.ssb.barn.framework.ValidationContext
import no.ssb.barn.report.WarningLevel
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

import java.time.LocalDate

import static no.ssb.barn.testutil.TestDataProvider.getTestContext

class MessageProcessingTimeOverdueSpec extends Specification {

    @Subject
    MessageProcessingTimeOverdue sut

    ValidationContext context

    @SuppressWarnings('unused')
    def setup() {
        sut = new MessageProcessingTimeOverdue()
        context = getTestContext()
    }

    @Unroll
    def "test av alle scenarier"() {
        given:
        def message = context.rootObject.sak.virksomhet[0].melding[0]
        and:
        message.startDato = startDate
        and:
        if (resetConclusion) {
            message.konklusjon = null
        } else {
            message.konklusjon?.sluttDato = endDate
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
            assert reportEntries[0].errorText.contains("Fristoverskridelse på behandlingstid for melding")
        }

        where:
        resetConclusion | startDate   | endDate     || errorExpected
        true            | getDate(-7) | getDate(0)  || false
        false           | getDate(-3) | getDate(0)  || false
        false           | getDate(-9) | getDate(-1) || true
        false           | getDate(-7) | getDate(0)  || false
    }

    static def getDate(days) {
        LocalDate.now().plusDays(days)
    }
}
