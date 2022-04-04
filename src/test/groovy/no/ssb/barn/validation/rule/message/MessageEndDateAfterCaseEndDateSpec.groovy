package no.ssb.barn.validation.rule.message

import no.ssb.barn.report.WarningLevel
import no.ssb.barn.validation.ValidationContext
import no.ssb.barn.xsd.MeldingKonklusjonType
import spock.lang.*

import java.time.LocalDate

import static no.ssb.barn.testutil.TestDataProvider.getTestContext

@Narrative("""
Melding Kontroll 2c: SluttDato er etter sakens SluttDato

Gitt at man har en melding der SluttDato finnes og i sak der SluttDato finnes
når meldingens SluttDato er etter sakens SluttDato
så gi feilmeldingen "Meldingen sluttdato {SluttDato} er etter sakens sluttdato {SluttDato}"

Alvorlighetsgrad: ERROR
""")
class MessageEndDateAfterCaseEndDateSpec extends Specification {

    @Subject
    MessageEndDateAfterCaseEndDate sut

    ValidationContext context

    @SuppressWarnings('unused')
    def setup() {
        sut = new MessageEndDateAfterCaseEndDate()
        context = getTestContext()
    }

    @Unroll
    def "Test av alle scenarier"() {
        given:
        context.rootObject.sak.sluttDato = businessEndDate
        and:
        def message = context.rootObject.sak.melding[0]
        and:
        if (resetConclusion) {
            message.konklusjon = null
        } else {
            message.konklusjon = new MeldingKonklusjonType(messageEndDate, "2")
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
            assert reportEntries[0].errorText.contains("er etter sakens")
        }

        where:
        businessEndDate | messageEndDate              | resetConclusion || errorExpected
        LocalDate.now() | LocalDate.now()             | false           || false
        LocalDate.now() | LocalDate.now().plusDays(1) | false           || true
        LocalDate.now() | LocalDate.now().plusDays(1) | true            || false
    }
}
