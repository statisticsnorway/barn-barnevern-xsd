package no.ssb.barn.validation.rule

import no.ssb.barn.validation.ValidationContext
import no.ssb.barn.report.WarningLevel
import no.ssb.barn.xsd.SaksinnholdType
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

import static no.ssb.barn.testutil.TestDataProvider.getTestContext

class InvestigationDecisionClarificationRequiredSpec extends Specification {

    @Subject
    InvestigationDecisionClarificationRequired sut

    ValidationContext context

    @SuppressWarnings('unused')
    def setup() {
        sut = new InvestigationDecisionClarificationRequired()
        context = getTestContext()
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
        context.rootObject.sak.undersokelse[0].vedtaksgrunnlag =
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
        code | clarification   || errorExpected
        null | "N/A"           || false
        ""   | "N/A"           || false
        "42" | "N/A"           || false

        "18" | null            || true
        "18" | ""              || true
        "19" | null            || true
        "19" | ""              || true

        "18" | "~presisering~" || false
        "19" | "~presisering~" || false
    }
}
