package no.ssb.barn.validation.rule

import no.ssb.barn.framework.ValidationContext
import no.ssb.barn.report.WarningLevel
import no.ssb.barn.xsd.SaksinnholdType
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

import static no.ssb.barn.testutil.TestDataProvider.getTestContext

class InvestigationDecisionRequiredSpec extends Specification {

    @Subject
    InvestigationDecisionRequired sut

    ValidationContext context

    @SuppressWarnings('unused')
    def setup() {
        sut = new InvestigationDecisionRequired()
        context = getTestContext()
    }

    @Unroll
    def "Test av alle scenarier"() {
        given:
        context.rootObject.sak.virksomhet[0].undersokelse[0].konklusjon.kode = code
        and:
        context.rootObject.sak.virksomhet[0].undersokelse[0].vedtaksgrunnlag = decision

        when:
        def reportEntries = sut.validate(context)

        then:
        (reportEntries != null) == errorExpected
        and:
        if (errorExpected) {
            assert 1 == reportEntries.size()
            assert WarningLevel.ERROR == reportEntries[0].warningLevel
            assert reportEntries[0].errorText.contains("skal ha vedtaksgrunnlag")
        }

        where:
        code | decision                       || errorExpected
        "1"  | null                           || true
        "1"  | List.of(new SaksinnholdType()) || false
        "2"  | null                           || true
        "2"  | List.of(new SaksinnholdType()) || false
        "3"  | null                           || false
    }
}
