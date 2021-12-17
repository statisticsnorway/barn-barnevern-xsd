package no.ssb.barn.validation.rule

import no.ssb.barn.framework.ValidationContext
import no.ssb.barn.report.WarningLevel
import no.ssb.barn.xsd.MelderType
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
        if (resetReporters) {
            melding.melder.clear()
        } else {
            melding.melder.add(new MelderType())
        }
        and:
        if (resetConclusion) {
            melding.konklusjon = null
        } else {
            melding.konklusjon.kode = code
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
        resetReporters | resetConclusion | code  || errorExpected
        false          | false           | "N/A" || false
        true           | true            | "N/A" || false
        true           | false           | "42"  || false
        true           | false           | "1"   || true
        true           | false           | "2"   || true
    }
}
