package no.ssb.barn.validation.rule

import no.ssb.barn.framework.ValidationContext
import no.ssb.barn.report.WarningLevel
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

import static no.ssb.barn.testutil.TestDataProvider.getTestContext

class MessageCaseContentContainsClarificationSpec extends Specification {
    @Subject
    MessageCaseContentContainsClarification sut

    ValidationContext context

    @SuppressWarnings('unused')
    def setup() {
        sut = new MessageCaseContentContainsClarification()
        context = getTestContext()
    }

    @Unroll
    def "Test av alle scenarier"() {
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
        (reportEntries != null) == errorExpected
        and:
        if (errorExpected) {
            assert 1 == reportEntries.size()
            assert WarningLevel.ERROR == reportEntries[0].warningLevel
            assert reportEntries[0].errorText.contains("mangler presisering")
        }

        where:
        code | clarification   || errorExpected
        "18" | "~presisering~" || false
        "19" | "~presisering~" || false
        "42" | "~presisering~" || false
        "42" | null            || false
        "18" | null            || true
        "19" | null            || true
    }
}
