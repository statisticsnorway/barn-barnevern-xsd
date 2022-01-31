package no.ssb.barn.validation.rule

import no.ssb.barn.report.WarningLevel
import no.ssb.barn.validation.ValidationContext
import spock.lang.Narrative
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

import java.time.LocalDate

import static no.ssb.barn.testutil.TestDataProvider.getTestContext

@Narrative("""
Melding Kontroll 2c: SluttDato er etter virksomhetens SluttDato

Gitt at man har en melding der SluttDato finnes og i virksomhet der SluttDato finnes
når meldingens SluttDato er etter virksomhetens SluttDato
så gi feilmeldingen "Meldingen sluttdato {SluttDato} er etter Virksomhetens sluttdato {SluttDato}"

Alvorlighetsgrad: ERROR
""")
class MessageEndDateAfterBusinessEndDateSpec extends Specification {

    @Subject
    MessageEndDateAfterBusinessEndDate sut

    ValidationContext context

    @SuppressWarnings('unused')
    def setup() {
        sut = new MessageEndDateAfterBusinessEndDate()
        context = getTestContext()
    }

    @Unroll
    def "Test av alle scenarier"() {
        given:
        context.rootObject.sak.virksomhet[0].sluttDato = businessEndDate
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
            assert reportEntries[0].errorText.contains("er etter virksomhetens")
        }

        where:
        businessEndDate | messageEndDate              | resetConclusion || errorExpected
        LocalDate.now() | LocalDate.now()             | false           || false
        LocalDate.now() | LocalDate.now().plusDays(1) | false           || true
        LocalDate.now() | LocalDate.now().plusDays(1) | true            || false
    }
}
