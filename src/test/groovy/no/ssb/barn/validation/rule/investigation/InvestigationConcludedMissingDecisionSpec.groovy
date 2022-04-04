package no.ssb.barn.validation.rule.investigation

import no.ssb.barn.validation.ValidationContext
import no.ssb.barn.report.WarningLevel
import no.ssb.barn.xsd.SaksinnholdType
import no.ssb.barn.xsd.UndersokelseKonklusjonType
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
        def investigation = context.rootObject.sak.undersokelse[0]
        and:
        investigation.vedtaksgrunnlag.clear()
        and:
        investigation.vedtaksgrunnlag.addAll(decision)
        and:
        if (resetConclusion) {
            investigation.konklusjon = null
        } else {
            investigation.konklusjon = new UndersokelseKonklusjonType(
                    investigation.konklusjon.sluttDato,
                    code,
                    investigation.konklusjon.presisering)
        }

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
        code | decision                  | resetConclusion || errorExpected
        "1"  | []                        | true            || false
        "1"  | [createSaksinnholdType()] | false           || false
        "2"  | []                        | false           || true
        "2"  | [createSaksinnholdType()] | false           || false
        "3"  | []                        | false           || false
    }

    def createSaksinnholdType() {
        new SaksinnholdType("1", null)
    }
}
