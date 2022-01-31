package no.ssb.barn.validation.rule

import no.ssb.barn.validation.ValidationContext
import spock.lang.Narrative
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

import java.time.LocalDate

import static no.ssb.barn.testutil.TestDataProvider.getTestContext

@Narrative("""
Melding Kontroll 2e: StartDato er før virksomhetens StartDato

Gitt at man har en melding med StartDato og i virksomhet med StartDato
når meldingens StartDato er før virksomhetens StartDato
så gi feilmeldingen "Meldingens startdato {StartDato} er før virksomhetens startdato {StartDato}"

Alvorlighetsgrad: ERROR
""")
class MessageStartDateBeforeBusinessStartDateSpec extends Specification {

    @Subject
    MessageStartDateBeforeBusinessStartDate sut

    ValidationContext context

    @SuppressWarnings('unused')
    def setup() {
        sut = new MessageStartDateBeforeBusinessStartDate()
        context = getTestContext()
    }

    @Unroll
    def "Test av alle scenarier"() {
        given:
        context.rootObject.sak.virksomhet[0].startDato = businessStartDate
        and:
        context.rootObject.sak.virksomhet[0].melding[0].startDato = messageStartDate

        when:
        def reportEntries = sut.validate(context)

        then:
        (reportEntries != null) == errorExpected

        where:
        businessStartDate             | messageStartDate              || errorExpected
        LocalDate.now().minusYears(1) | LocalDate.now()               || false
        LocalDate.now()               | LocalDate.now()               || false
        LocalDate.now()               | LocalDate.now().minusYears(1) || true
    }
}
