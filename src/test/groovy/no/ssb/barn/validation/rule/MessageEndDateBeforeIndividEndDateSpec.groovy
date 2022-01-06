package no.ssb.barn.validation.rule

import no.ssb.barn.validation.ValidationContext
import no.ssb.barn.report.WarningLevel
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

import java.time.LocalDate

import static no.ssb.barn.testutil.TestDataProvider.getTestContext

class MessageEndDateBeforeIndividEndDateSpec extends Specification {

    @Subject
    MessageEndDateBeforeIndividEndDate sut

    ValidationContext context

    @SuppressWarnings('unused')
    def setup() {
        sut = new MessageEndDateBeforeIndividEndDate()
        context = getTestContext()
    }

    @Unroll
    def "Test av alle scenarier"() {
        given:
        context.rootObject.sak.sluttDato = caseEndDate
        and:
        def message = context.rootObject.sak.virksomhet[0].melding[0]
        and:
        if (resetConclusion) {
            message.konklusjon = null
        } else {
            message.konklusjon.sluttDato = messageEndDate
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
            assert reportEntries[0].errorText.contains("er etter individets")
        }

        where:
        caseEndDate     | messageEndDate              | resetConclusion || errorExpected
        LocalDate.now() | LocalDate.now()             | false           || false
        LocalDate.now() | LocalDate.now().plusDays(1) | false           || true
        LocalDate.now() | LocalDate.now().plusDays(1) | true            || false
    }
}
