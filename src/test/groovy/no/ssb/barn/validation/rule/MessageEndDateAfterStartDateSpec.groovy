package no.ssb.barn.validation.rule

import no.ssb.barn.framework.ValidationContext
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
    def "Startdato suksess-scenarier, ingen feil forventes"() {
        given:
        if (resetConclusion) {
            context.rootObject.sak.virksomhet[0].melding[0].konklusjon = null
        }

        when:
        def reportEntries = sut.validate(context)

        then:
        noExceptionThrown()
        and:
        null == reportEntries

        where:
        resetConclusion | _
        false           | _
        false           | _
        true            | _
    }

    def "Startdato etter sluttdato, feil forventes"() {
        given:
        context.rootObject.sak.virksomhet[0].melding[0].startDato = LocalDate.now()

        when:
        def reportEntries = sut.validate(context)

        then:
        noExceptionThrown()
        and:
        1 == reportEntries.size()
    }
}
