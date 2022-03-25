package no.ssb.barn.validation.rule.investigation

import no.ssb.barn.report.WarningLevel
import no.ssb.barn.validation.ValidationContext
import no.ssb.barn.xsd.SaksinnholdType
import spock.lang.Narrative
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

import static no.ssb.barn.testutil.TestDataProvider.getTestContext

@Narrative("""
Undersøkelse Kontroll 3: Vedtaksgrunnlag mangler presisering

Gitt at man har et Vedtaksgrunnlag der Kode er 18 (= Andre forhold ved foreldre/familien) eller 19 (= Andre forhold ved barnets situasjon)<br/>
når Vedtaksgrunnlag mangler Presisering<br/>
så gi feilmeldingen "Vedtaksgrunnlag med kode ({Kode}) mangler presisering"

Alvorlighetsgrad: ERROR
""")
class InvestigationDecisionMissingClarificationSpec extends Specification {

    @Subject
    InvestigationDecisionMissingClarification sut

    ValidationContext context

    @SuppressWarnings('unused')
    def setup() {
        sut = new InvestigationDecisionMissingClarification()
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
        context.rootObject.sak.undersokelse[0].vedtaksgrunnlag = [decision]

        when:
        def reportEntries = sut.validate(context)

        then:
        (reportEntries != null) == errorExpected
        and:
        if (errorExpected) {
            assert 1 == reportEntries.size()
            assert WarningLevel.ERROR == reportEntries[0].warningLevel
            assert reportEntries[0].errorText.contains("mangler Presisering")
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
