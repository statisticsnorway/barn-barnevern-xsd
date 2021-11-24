package no.ssb.barn.validation.rule

import no.ssb.barn.framework.ValidationContext
import no.ssb.barn.testutil.TestDataProvider
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

class MessageCaseContentContainsClarificationSpec extends Specification {
    @Subject
    MessageCaseContentContainsClarification sut

    ValidationContext context

    @SuppressWarnings('unused')
    def setup() {
        sut = new MessageCaseContentContainsClarification()
        context = TestDataProvider.getTestContext()
    }

    @Unroll
    def "Saksinnhold suksess-scenarier, ingen feil forventes"() {
        given:
        def saksinnhold = context.rootObject.sak.virksomhet[0].melding[0].saksinnhold[0]
        and:
        saksinnhold.kode = code
        and:
        saksinnhold.presisering = clarification

        when:
        def reportEntries = sut.validate(context)

        then:
        noExceptionThrown()
        and:
        null == reportEntries

        where:
        code | clarification
        "18" | "~presisering~"
        "19" | "~presisering~"
        "42" | "~presisering~"
        "42" | null
    }

    @Unroll
    def "Saksinnhold mangler presisering, feil forventes"() {
        given:
        def saksinnhold = context.rootObject.sak.virksomhet[0].melding[0].saksinnhold[0]
        and:
        saksinnhold.kode = "18"
        and:
        saksinnhold.presisering = null

        when:
        def reportEntries = sut.validate(context)

        then:
        noExceptionThrown()
        and:
        1 == reportEntries.size()

        where:
        code | _
        "18" | _
        "19" | _
    }
}
