package no.ssb.barn.validation.rule

import no.ssb.barn.framework.ValidationContext
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

import static no.ssb.barn.testutil.TestDataProvider.getTestContext

class HasContentSpec extends Specification {

    @Subject
    HasContent sut

    ValidationContext context

    @SuppressWarnings('unused')
    def setup() {
        sut = new HasContent()
        context = getTestContext()
    }

    @Unroll
    def "Suksess-scenarier, ingen feil forventes"() {
        when:
        def reportEntries = sut.validate(context)

        then:
        noExceptionThrown()
        and:
        null == reportEntries
    }

    def "Mangler Melding og Tiltak og Plan, feil forventes"() {
        given:
        context.rootObject.sak.virksomhet = List.of(context.rootObject.sak.virksomhet[0])
        and:
        context.rootObject.sak.virksomhet[0].melding = null

        when:
        def reportEntries = sut.validate(context)

        then:
        noExceptionThrown()
        and:
        1 == reportEntries.size()
    }
}