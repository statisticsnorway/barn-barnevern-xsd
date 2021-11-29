package no.ssb.barn.validation.rule

import no.ssb.barn.framework.ValidationContext
import no.ssb.barn.report.WarningLevel
import no.ssb.barn.testutil.TestDataProvider
import no.ssb.barn.xsd.SaksinnholdType
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

class InvestigationDecisionClarificationRequiredSpec extends Specification {

    @Subject
    InvestigationDecisionClarificationRequired sut

    ValidationContext context

    @SuppressWarnings('unused')
    def setup() {
        sut = new InvestigationDecisionClarificationRequired()
        context = TestDataProvider.getTestContext()
    }

    @Unroll
    def "Test av alle scenarier"() {
        given:
        def decision = new SaksinnholdType()
        and:
        decision.kode = code
        and:
        decision.presisering = clarification
        and:
        context.rootObject.sak.virksomhet[0].undersokelse[0].vedtaksgrunnlag =
                List.of(decision)

        when:
        def reportEntries = sut.validate(context)

        then:
        (reportEntries != null) == errorExpected
        and:
        if (errorExpected) {
            assert 1 == reportEntries.size()
            assert WarningLevel.ERROR == reportEntries[0].warningLevel
            assert reportEntries[0].errorText.contains("Vedtaksgrunnlag med kode")
        }

        where:
        code | clarification     || errorExpected
        "18" | null              || true
        "18" | "~clarification~" || false
        "19" | null              || true
        "19" | "~clarification~" || false
        "20" | null              || false
    }
}
