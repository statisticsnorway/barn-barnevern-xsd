package no.ssb.barn.validation.rule

import no.ssb.barn.framework.ValidationContext
import no.ssb.barn.testutil.TestDataProvider
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

import java.time.LocalDate

class MessageEndDateBeforeIndividEndDateSpec extends Specification {

    @Subject
    MessageEndDateBeforeIndividEndDate sut

    ValidationContext context

    @SuppressWarnings('unused')
    def setup() {
        sut = new MessageEndDateBeforeIndividEndDate()
        context = TestDataProvider.getTestContext()
    }

    @Unroll
    def "Sluttdato suksess-scenarier, ingen feil forventes"() {
        given:
        context.rootObject.sak.sluttDato = endDate
        and:
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
        endDate         | resetConclusion
        LocalDate.now() | false
        null            | false
        LocalDate.now() | true
    }

    def "Sluttdato for melding etter sluttdato for individ, feil forventes"() {
        given:
        context.rootObject.sak.sluttDato = LocalDate.now().minusYears(1)
        and:
        context.rootObject.sak.virksomhet[0].melding[0].startDato = LocalDate.now()

        when:
        def reportEntries = sut.validate(context)

        then:
        noExceptionThrown()
        and:
        1 == reportEntries.size()
    }
}
