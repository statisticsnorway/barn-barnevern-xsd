package no.ssb.barn.validation.rule.plan

import no.ssb.barn.report.WarningLevel
import no.ssb.barn.validation.ValidationContext
import no.ssb.barn.xsd.PlanKonklusjonType
import no.ssb.barn.xsd.PlanType
import spock.lang.Narrative
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

import java.time.LocalDate

import static no.ssb.barn.testutil.TestDataProvider.getTestContext

@Narrative("""
Plan Kontroll 2e: StartDato er før sakens StartDato

Gitt at man har en Plan der StartDato finnes og sak der StartDato finnes<br/>
når planens StartDato er før sakens StartDato <br/>
så gi feilmeldingen "Planens startdato {StartDato } er før sakens startdato {StartDato }"

Alvorlighetsgrad: ERROR
""")
class PlanStartDateBeforeCaseStartDateSpec extends Specification {

    @Subject
    PlanStartDateBeforeCaseStartDate sut

    ValidationContext context

    @SuppressWarnings('unused')
    def setup() {
        sut = new PlanStartDateBeforeCaseStartDate()
        context = getTestContext()
    }

    @Unroll
    def "Test av alle scenarier"() {
        given:
        context.rootObject.sak.startDato = caseStartDate
        and:
        context.rootObject.sak.plan[0] = createPlanType(planStartDate)

        when:
        def reportEntries = sut.validate(context)

        then:
        (reportEntries != null) == errorExpected
        and:
        if (errorExpected) {
            assert 1 == reportEntries.size()
            assert WarningLevel.ERROR == reportEntries.first().warningLevel
            assert reportEntries[0].errorText.contains("er før sakens startdato")
        }

        where:
        caseStartDate                 | planStartDate                 || errorExpected
        LocalDate.now().minusYears(1) | LocalDate.now()               || false
        LocalDate.now()               | LocalDate.now()               || false
        LocalDate.now()               | LocalDate.now().minusYears(1) || true
    }

    def createPlanType(planStartDate) {
        new PlanType(
                UUID.randomUUID(),
                null,
                planStartDate,
                "1",
                [],
                null)
    }
}
