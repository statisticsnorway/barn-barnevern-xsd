package no.ssb.barn.validation.rule

import no.ssb.barn.report.WarningLevel
import no.ssb.barn.validation.ValidationContext
import spock.lang.Narrative
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

import java.time.LocalDateTime

import static no.ssb.barn.testutil.TestDataProvider.getTestContext

@Narrative("""
Melding Kontroll 2a: StartDato er etter SluttDato

Gitt at man har en melding der StartDato og Konklusjon/SluttDato finnes<br/>
når StartDato er etter SluttDato<br/>
så gi feilmeldingen "Meldingens startdato {StartDato} er etter sluttdato {Konklusjon/SluttDato}"

Alvorlighetsgrad: ERROR
""")
class MessageStartDateAfterEndDateSpec extends Specification {

    @Subject
    MessageStartDateAfterEndDate sut

    ValidationContext context

    @SuppressWarnings('unused')
    def setup() {
        sut = new MessageStartDateAfterEndDate()
        context = getTestContext()
    }

    @Unroll
    def "Test av alle scenarier"() {
        given:
        def message = context.rootObject.sak.melding[0]
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
        resetConclusion | startDate                       | endDate                         || errorExpected
        false           | LocalDateTime.now()             | LocalDateTime.now()             || false
        false           | LocalDateTime.now()             | LocalDateTime.now().plusDays(1) || false
        true            | LocalDateTime.now()             | LocalDateTime.now()             || false
        false           | LocalDateTime.now().plusDays(1) | LocalDateTime.now()             || true
    }
}
