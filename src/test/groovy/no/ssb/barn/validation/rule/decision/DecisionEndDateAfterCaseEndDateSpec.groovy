package no.ssb.barn.validation.rule.decision

import no.ssb.barn.report.WarningLevel
import no.ssb.barn.validation.ValidationContext
import no.ssb.barn.xsd.VedtakKonklusjonType
import spock.lang.Narrative
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

import java.time.LocalDate

import static no.ssb.barn.testutil.TestDataProvider.getTestContext

@Narrative("""
Vedtak Kontroll 2c: SluttDato mot sakens SluttDato

Gitt at man har et Vedtak der Konklusjon/SluttDato finnes og i sak der SluttDato finnes<br/>
når vedtakets SluttDato er etter sakens SluttDato<br/>
så gi feilmeldingen "Vedtakets sluttdato {Konklusjon/SluttDato} er etter sakens sluttdato {SluttDato}

Alvorlighetsgrad: ERROR
""")
class DecisionEndDateAfterCaseEndDateSpec extends Specification {

    @Subject
    DecisionEndDateAfterCaseEndDate sut

    ValidationContext context

    @SuppressWarnings('unused')
    def setup() {
        sut = new DecisionEndDateAfterCaseEndDate()
        context = getTestContext()
    }

    @Unroll
    def "Test av alle scenarier"() {
        given:
        context.rootObject.sak.sluttDato = caseEndDate
        and:
        def decision = context.rootObject.sak.vedtak.first()
        and:
        context.rootObject.sak.vedtak = [decision]
        and:
        if (resetConclusion) {
            decision.konklusjon = null
        } else {
            decision.konklusjon = new VedtakKonklusjonType(decisionEndDate)
        }

        when:
        def reportEntries = sut.validate(context)

        then:
        (reportEntries != null) == errorExpected
        and:
        if (errorExpected) {
            assert 1 == reportEntries.size()
            assert WarningLevel.ERROR == reportEntries[0].warningLevel
            assert reportEntries[0].errorText.contains("er etter sakens sluttdato")
        }

        where:
        resetConclusion | caseEndDate                   | decisionEndDate               || errorExpected
        false           | null                          | LocalDate.now()               || false
        false           | LocalDate.now().minusYears(1) | LocalDate.now()               || true
        true            | LocalDate.now().minusYears(1) | LocalDate.now()               || false
        false           | LocalDate.now()               | LocalDate.now()               || false
        true            | LocalDate.now()               | LocalDate.now()               || false
        false           | LocalDate.now()               | LocalDate.now().minusYears(1) || false
        true            | LocalDate.now()               | LocalDate.now().minusYears(1) || false
    }
}
