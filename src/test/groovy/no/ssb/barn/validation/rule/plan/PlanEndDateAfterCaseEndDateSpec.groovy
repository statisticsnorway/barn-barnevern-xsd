package no.ssb.barn.validation.rule.plan

import no.ssb.barn.report.WarningLevel
import no.ssb.barn.validation.ValidationContext
import no.ssb.barn.xsd.PlanKonklusjonType
import no.ssb.barn.xsd.PlanType
import spock.lang.*

import java.time.LocalDate

import static no.ssb.barn.testutil.TestDataProvider.getTestContext

@Narrative("""
Plan Kontroll 2c: SluttDato er etter sakens SluttDato

Gitt at man har en Plan der Konklusjon/SluttDato finnes og i sak der SluttDato finnes<br/>
når planens SluttDato er etter sakens SluttDato<br/>
så gi feilmeldingen "Planens sluttdato {Konklusjon/SluttDato} er etter sakens sluttdato {SluttDato}"

Alvorlighetsgrad: ERROR
""")
class PlanEndDateAfterCaseEndDateSpec extends Specification {

    @Subject
    PlanEndDateAfterCaseEndDate sut

    ValidationContext context

    @SuppressWarnings('unused')
    def setup() {
        sut = new PlanEndDateAfterCaseEndDate()
        context = getTestContext()
    }

    @Unroll
    def "Test av alle scenarier"() {
        given:
        context.rootObject.sak.sluttDato = caseEndDate
        and:
        context.rootObject.sak.plan[0] = createPlanType(caseEndDate, planEndDate, resetConclusion)

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
            assert reportEntries[0].errorText.contains("er etter sakens")
        }

        where:
        caseEndDate     | planEndDate                 | resetConclusion || errorExpected
        LocalDate.now() | LocalDate.now()             | false           || false
        LocalDate.now() | LocalDate.now().plusDays(1) | false           || true
        LocalDate.now() | LocalDate.now().plusDays(1) | true            || false
    }

    def createPlanType(planStartDate, planEndDate, resetConclusion) {
        new PlanType(
                UUID.randomUUID(),
                null,
                planStartDate,
                "1",
                [],
                resetConclusion ? null : new PlanKonklusjonType(planEndDate))
    }

}
