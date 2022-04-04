package no.ssb.barn.validation.rule.plan

import no.ssb.barn.report.WarningLevel
import no.ssb.barn.validation.ValidationContext
import no.ssb.barn.xsd.PlanEvalueringType
import no.ssb.barn.xsd.PlanType
import spock.lang.Narrative
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

import java.time.LocalDate

import static no.ssb.barn.testutil.TestDataProvider.getTestContext

@Narrative("""
Plan Kontroll 2g: UtfortDato er før sakens StartDato

Gitt at man har en Plan der Evaluering/UtfortDato finnes<br/>
når UtfortDato er før StartDato<br/>
så gi feilmeldingen "Utført evaluering {Evaluering/UtfortDato} er før startdato {StartDato}

Alvorlighetsgrad: ERROR
""")
class PlanEvaluationDateBeforeCaseStartDateSpec extends Specification {

    @Subject
    PlanEvaluationDateBeforeCaseStartDate sut

    ValidationContext context

    @SuppressWarnings('unused')
    def setup() {
        sut = new PlanEvaluationDateBeforeCaseStartDate()
        context = getTestContext()
    }

    @Unroll
    def "Test av alle scenarier"() {
        given:
        context.rootObject.sak.startDato = caseStartDate
        and:
        context.rootObject.sak.plan[0] = createPlanType(caseStartDate, executedDate, resetEvaluation)

        when:
        def reportEntries = sut.validate(context)

        then:
        noExceptionThrown()
        and:
        (reportEntries != null) == errorExpected
        and:
        if (errorExpected) {
            assert 1 == reportEntries.size()
            assert WarningLevel.ERROR == reportEntries.first().warningLevel
            assert reportEntries[0].errorText.contains("Utført evaluering")
        }

        where:
        caseStartDate               | executedDate                | resetEvaluation || errorExpected
        LocalDate.now().plusDays(1) | LocalDate.now()             | false           || true
        LocalDate.now()             | LocalDate.now().plusDays(1) | false           || false
        LocalDate.now()             | LocalDate.now().plusDays(1) | true            || false
    }

    def createPlanType(planStartDate, executedDate, resetEvaluation) {
        new PlanType(
                UUID.randomUUID(),
                null,
                planStartDate,
                "1",
                resetEvaluation ? [] : [new PlanEvalueringType(executedDate)],
                null)
    }
}
