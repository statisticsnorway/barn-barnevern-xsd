package no.ssb.barn.validation.rule

import no.ssb.barn.framework.ValidationContext
import no.ssb.barn.report.WarningLevel
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

import static no.ssb.barn.testutil.TestDataProvider.getTestContext

class MessageContainsReportersSpec extends Specification {

    @Subject
    MessageContainsReporters sut

    ValidationContext context

    @SuppressWarnings('unused')
    def setup() {
        sut = new MessageContainsReporters()
        context = getTestContext()
    }

    @Unroll
    def "Test av alle scenarier"() {
        given:
        def melding = context.rootObject.sak.virksomhet[0].melding[0]
        and:
        melding.konklusjon.kode = code
        and:
        if (resetReporters) {
            melding.melder = List.of()
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
            assert reportEntries[0].errorText.contains("Konkludert melding mangler melder(e).")
        }

        where:
        code | resetReporters || errorExpected
        "1"  | false          || false
        "2"  | false          || false
        "1"  | true           || true
        "2"  | true           || true
        "42" | true           || false
    }
}
