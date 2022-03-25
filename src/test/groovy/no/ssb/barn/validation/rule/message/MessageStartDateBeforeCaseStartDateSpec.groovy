package no.ssb.barn.validation.rule.message

import no.ssb.barn.report.WarningLevel
import no.ssb.barn.validation.ValidationContext
import no.ssb.barn.validation.rule.message.MessageStartDateBeforeCaseStartDate
import spock.lang.*

import java.time.ZonedDateTime

import static no.ssb.barn.testutil.TestDataProvider.getTestContext

@Narrative("""
Melding Kontroll 2e: StartDato er før sakens StartDato

Gitt at man har en melding med StartDato og i sak med StartDato
når meldingens StartDato er før sakens StartDato
så gi feilmeldingen "Meldingens startdato {StartDato} er før sakens startdato {StartDato}"

Alvorlighetsgrad: ERROR
""")
class MessageStartDateBeforeCaseStartDateSpec extends Specification {

    @Subject
    MessageStartDateBeforeCaseStartDate sut

    ValidationContext context

    @SuppressWarnings('unused')
    def setup() {
        sut = new MessageStartDateBeforeCaseStartDate()
        context = getTestContext()
    }

    @Unroll
    def "Test av alle scenarier"() {
        given:
        context.rootObject.sak.startDato = businessStartDate
        and:
        context.rootObject.sak.melding[0].startDato = messageStartDate

        when:
        def reportEntries = sut.validate(context)

        then:
        (reportEntries != null) == errorExpected
        and:
        if (errorExpected) {
            assert 1 == reportEntries.size()
            assert WarningLevel.ERROR == reportEntries[0].warningLevel
            assert reportEntries[0].errorText.contains("er før sakens")
        }

        where:
        businessStartDate                 | messageStartDate                  || errorExpected
        ZonedDateTime.now().minusYears(1) | ZonedDateTime.now()               || false
        ZonedDateTime.now()               | ZonedDateTime.now()               || false
        ZonedDateTime.now()               | ZonedDateTime.now().minusYears(1) || true
    }
}
