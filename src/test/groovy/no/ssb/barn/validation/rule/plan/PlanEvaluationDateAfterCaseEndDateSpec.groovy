package no.ssb.barn.validation.rule.plan

import no.ssb.barn.report.WarningLevel
import no.ssb.barn.validation.ValidationContext
import no.ssb.barn.xsd.PlanEvalueringType
import spock.lang.Narrative
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

import java.time.LocalDate

import static no.ssb.barn.testutil.TestDataProvider.getTestContext

@Narrative("""
Plan Kontroll 2f: UtfortDato er etter sakens SluttDato

Gitt at man har en Plan der Evaluering/UtfortDato finnes og Konklusjon/SluttDato finnes<br/>
når UtfortDato er etter SluttDato<br/>
så gi feilmeldingen "Utført evaluering {Evaluering/UtfortDato} er etter sluttdato {Konklusjon/SluttDato}

Alvorlighetsgrad: ERROR
""")
class PlanEvaluationDateAfterCaseEndDateSpec extends Specification {

    @Subject
    PlanEvaluationDateAfterCaseEndDate sut

    ValidationContext context

    @SuppressWarnings('unused')
    def setup() {
        sut = new PlanEvaluationDateAfterCaseEndDate()
        context = getTestContext()
    }

    @Unroll
    def "Test av alle scenarier"() {
        given:
        context.rootObject.sak.sluttDato = caseEndDate
        and:
        def plan = context.rootObject.sak.plan.first()
        and:
        if (resetEvaluation) {
            plan.evaluering.removeAll()
        } else {
            plan.evaluering[0] = new PlanEvalueringType(executedDate)
        }

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
        caseEndDate     | executedDate                | resetEvaluation || errorExpected
        null            | LocalDate.now()             | false           || false
        LocalDate.now() | LocalDate.now()             | false           || false
        LocalDate.now() | LocalDate.now().plusDays(1) | false           || true
        LocalDate.now() | LocalDate.now().plusDays(1) | true            || false
    }
}
