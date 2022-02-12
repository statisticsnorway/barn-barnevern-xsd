package no.ssb.barn.validation.rule

import no.ssb.barn.validation.ValidationContext
import no.ssb.barn.report.WarningLevel
import no.ssb.barn.xsd.SaksinnholdType
import spock.lang.Narrative
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

import static no.ssb.barn.testutil.TestDataProvider.getTestContext

@Narrative("""
Undersøkelse Kontroll 7: Konkludert undersøkelse mangler vedtaksgrunnlag

Gitt at man har en Undersøkelse der Konklusjon finnes og Konklusjon sin Kode er 1 eller 2<br/>
når Vedtaksgrunnlag mangler<br/>
så gi feilmeldingen "Undersøkelse konkludert med kode {Konklusjon/Kode} mangler vedtaksgrunnlag"

Alvorlighetsgrad: ERROR
""")
class InvestigationConcludedMissingDecisionSpec extends Specification {

    @Subject
    InvestigationConcludedMissingDecision sut

    ValidationContext context

    @SuppressWarnings('unused')
    def setup() {
        sut = new InvestigationConcludedMissingDecision()
        context = getTestContext()
    }

    @Unroll
    def "Test av alle scenarier"() {
        given:
        def investigation = context.rootObject.sak.virksomhet[0].undersokelse[0]
        and:
        investigation.konklusjon.kode = code
        and:
        investigation.vedtaksgrunnlag = decision as List<SaksinnholdType>

        when:
        def reportEntries = sut.validate(context)

        then:
        (reportEntries != null) == errorExpected
        and:
        if (errorExpected) {
            assert 1 == reportEntries.size()
            assert WarningLevel.ERROR == reportEntries[0].warningLevel
            assert reportEntries[0].errorText.contains("mangler vedtaksgrunnlag")
        }

        where:
        code | decision                       || errorExpected
        "1"  | List.of()                      || true
        "1"  | List.of(new SaksinnholdType()) || false
        "2"  | List.of()                      || true
        "2"  | List.of(new SaksinnholdType()) || false
        "3"  | List.of()                      || false
    }
}
