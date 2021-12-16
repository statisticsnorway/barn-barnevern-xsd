package no.ssb.barn.validation.rule

import no.ssb.barn.framework.ValidationContext
import no.ssb.barn.report.WarningLevel
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

import java.time.LocalDate

import static no.ssb.barn.testutil.TestDataProvider.getTestContext

class MessageReporterContainsClarificationSpec extends Specification {

    @Subject
    MessageReporterContainsClarification sut

    ValidationContext context

    @SuppressWarnings('unused')
    def setup() {
        sut = new MessageReporterContainsClarification()
        context = getTestContext()
    }

    @Unroll
    def "Test av alle scenarier"() {
        given:
        def message = context.rootObject.sak.virksomhet[0].melding[0]
        and:
        message.konklusjon.sluttDato = messageEndDate
        and:
        message.melder[0].kode = code
        and:
        message.melder[0].presisering = clarification
        and:
        if (resetConclusion) {
            message.konklusjon = null
        }
        and:
        if (resetReporter) {
            message.melder = List.of()
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
            assert reportEntries[0].errorText.contains("Melder med kode")
        }

        where:
        resetConclusion | resetReporter | messageEndDate  | code  | clarification   || errorExpected
        true            | false         | LocalDate.now() | "N/A" | "N/A"           || false
        false           | true          | LocalDate.now() | "N/A" | "N/A"           || false
        false           | false         | LocalDate.now() | "1"   | "N/A"           || false
        false           | false         | LocalDate.now() | "22"  | "~presisering~" || false
        false           | false         | LocalDate.now() | "22"  | ""              || true
        false           | false         | LocalDate.now() | "22"  | null            || true
    }
}
