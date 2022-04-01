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
Vedtak Kontroll 2e: StartDato er før sakens StartDato

Gitt at man har et Vedtak der StartDato finnes og sak der StartDato finnes<br/>
når vedtakets StartDato er før sakens StartDato <br/>
så gi feilmeldingen "Vedtakets startdato {StartDato} er før sakens startdato {StartDato}

Alvorlighetsgrad: ERROR
""")
class DecisionStartDateBeforeCaseStartDateSpec extends Specification {

    @Subject
    DecisionStartDateBeforeCaseStartDate sut

    ValidationContext context

    @SuppressWarnings('unused')
    def setup() {
        sut = new DecisionStartDateBeforeCaseStartDate()
        context = getTestContext()
    }

    @Unroll
    def "Test av alle scenarier"() {
        given:
        def decision = context.rootObject.sak.vedtak.first()
        and:
        decision.startDato = decisionStartDate
        and:
        context.rootObject.sak.startDato = caseStartDate
        and:
        context.rootObject.sak.vedtak = [decision]

        when:
        def reportEntries = sut.validate(context)

        then:
        (reportEntries != null) == errorExpected
        and:
        if (errorExpected) {
            assert 1 == reportEntries.size()
            assert WarningLevel.ERROR == reportEntries[0].warningLevel
            assert reportEntries[0].errorText.contains("er før sakens startdato")
        }

        where:
        decisionStartDate             | caseStartDate                 || errorExpected
        LocalDate.now().minusYears(1) | LocalDate.now()               || true
        LocalDate.now()               | LocalDate.now()               || false
        LocalDate.now()               | LocalDate.now().minusYears(1) || false
    }
}
