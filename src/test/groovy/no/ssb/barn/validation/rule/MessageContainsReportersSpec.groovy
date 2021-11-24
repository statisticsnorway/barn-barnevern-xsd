package no.ssb.barn.validation.rule

import no.ssb.barn.framework.ValidationContext
import no.ssb.barn.testutil.TestDataProvider
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

class MessageContainsReportersSpec extends Specification {

    @Subject
    MessageContainsReporters sut

    ValidationContext context

    @SuppressWarnings('unused')
    def setup() {
        sut = new MessageContainsReporters()
        context = TestDataProvider.getTestContext()
    }

    @Unroll
    def "Melder suksess-scenarier, ingen feil forventes"() {
        given:
        def melding = context.rootObject.sak.virksomhet[0].melding[0]
        and:
        melding.konklusjon.kode = code
        and:
        if (resetReporters) {
            melding.melder = null
        }

        when:
        def reportEntries = sut.validate(context)

        then:
        noExceptionThrown()
        and:
        null == reportEntries

        where:
        code | resetReporters
        "1"  | false
        "2"  | false
        "42" | true
    }

    @Unroll
    def "Meldere mangler, feil forventes"() {
        given:
        def melding = context.rootObject.sak.virksomhet[0].melding[0]
        and:
        melding.melder = null
        and:
        melding.konklusjon.kode = code

        when:
        def reportEntries = sut.validate(context)

        then:
        noExceptionThrown()
        and:
        1 == reportEntries.size()

        where:
        code | _
        "1"  | _
        "2"  | _
    }
}
