package no.ssb.barn.validation.rule

import no.ssb.barn.validation.ValidationContext
import no.ssb.barn.report.WarningLevel
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

import java.time.LocalDate

import static no.ssb.barn.testutil.TestDataProvider.getTestContext

class MessageEndDateAfterStartDateSpec extends Specification {

    @Subject
    MessageEndDateAfterStartDate sut

    ValidationContext context

    @SuppressWarnings('unused')
    def setup() {
        sut = new MessageEndDateAfterStartDate()
        context = getTestContext()
    }

    @Unroll
    def "Test av alle scenarier"() {
        given:
        def message = context.rootObject.sak.virksomhet[0].melding[0]
        and:
        message.startDato = startDate
        and:
        if (resetConclusion) {
            message.konklusjon = null
        } else {
            message.konklusjon.sluttDato = endDate
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
            assert WarningLevel.ERROR == reportEntries[0].warningLevel
            assert reportEntries[0].errorText.contains("er etter meldingens sluttdato")
        }

        where:
        resetConclusion | startDate                   | endDate                     || errorExpected
        false           | LocalDate.now()             | LocalDate.now()             || false
        false           | LocalDate.now()             | LocalDate.now().plusDays(1) || false
        true            | LocalDate.now()             | LocalDate.now()             || false
        false           | LocalDate.now().plusDays(1) | LocalDate.now()             || true
    }
}
