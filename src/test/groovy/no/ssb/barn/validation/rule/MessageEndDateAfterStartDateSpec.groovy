package no.ssb.barn.validation.rule

import no.ssb.barn.framework.ValidationContext
import no.ssb.barn.testutil.TestDataProvider
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

import java.time.LocalDate

class MessageEndDateAfterStartDateSpec extends Specification {

    @Subject
    MessageEndDateAfterStartDate sut

    ValidationContext context

    @SuppressWarnings('unused')
    def setup() {
        sut = new MessageEndDateAfterStartDate()
        context = TestDataProvider.getTestContext()
    }

    @Unroll
    def "Startdato suksess-scenarier, ingen feil forventes"() {
        given:
        def melding = context.rootObject.sak.virksomhet[0].melding[0]
        and:
        if (resetConclusion) {
            melding.konklusjon = null
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
        def melding = context.rootObject.sak.virksomhet[0].melding[0]
        and:
        melding.startDato = LocalDate.now()

        when:
        def reportEntries = sut.validate(context)

        then:
        noExceptionThrown()
        and:
        1 == reportEntries.size()
    }
}
