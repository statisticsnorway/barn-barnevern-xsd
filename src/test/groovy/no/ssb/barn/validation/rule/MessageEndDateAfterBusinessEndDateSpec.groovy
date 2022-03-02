package no.ssb.barn.validation.rule

import no.ssb.barn.report.WarningLevel
import no.ssb.barn.validation.ValidationContext
import spock.lang.*

import java.time.LocalDateTime

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
    @Ignore("Fix me")
    def "Test av alle scenarier"() {
        given:
        context.rootObject.sak.sluttDato = businessEndDate
        and:
        def message = context.rootObject.sak.melding[0]
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
        LocalDateTime.now() | LocalDateTime.now() | false || false
        LocalDateTime.now() | LocalDateTime.now().plusDays(1) | false           || true
        LocalDateTime.now() | LocalDateTime.now().plusDays(1) | true            || false
    }
}
