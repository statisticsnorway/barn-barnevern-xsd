package no.ssb.barn.validation.rule.decision

import no.ssb.barn.report.WarningLevel
import no.ssb.barn.validation.ValidationContext
import spock.lang.Narrative
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

import java.time.LocalDate

import static no.ssb.barn.testutil.TestDataProvider.getTestContext

@Narrative("""
Vedtak Kontroll 2a: StartDato er etter SluttDato

Gitt at man har et Vedtak der StartDato og Konklusjon/SluttDato finnes<br/>
når StartDato er etter SluttDato<br/>
så gi feilmeldingen "Vedtakets startdato {StartDato} er etter sluttdato {Konklusjon/SluttDato}"

Alvorlighetsgrad: ERROR
""")
class DecisionStartDateAfterEndDateSpec extends Specification {

    @Subject
    DecisionStartDateAfterEndDate sut

    ValidationContext context

    @SuppressWarnings('unused')
    def setup() {
        sut = new DecisionStartDateAfterEndDate()
        context = getTestContext()
    }

    @Unroll
    def "Test av alle scenarier"() {
        given:
        def decision = context.rootObject.sak.vedtak.first()
        and:
        decision.startDato = decisionStartDate
        and:
        decision.konklusjon.sluttDato = decisionEndDate
        and:
        context.rootObject.sak.vedtak = [decision]
        and:
        if (removeConclusion) {
            decision.konklusjon = null
        }

        when:
        def reportEntries = sut.validate(context)

        then:
        (reportEntries != null) == errorExpected
        and:
        if (errorExpected) {
            assert 1 == reportEntries.size()
            assert WarningLevel.ERROR == reportEntries[0].warningLevel
            assert reportEntries[0].errorText.contains("er etter sluttdato")
        }

        where:
        decisionStartDate             | decisionEndDate               | removeConclusion || errorExpected
        LocalDate.now().minusYears(1) | LocalDate.now()               | false            || false
        LocalDate.now()               | LocalDate.now()               | false            || false
        LocalDate.now()               | LocalDate.now().minusYears(1) | false            || true
        LocalDate.now()               | LocalDate.now().minusYears(1) | true             || false
    }
}
