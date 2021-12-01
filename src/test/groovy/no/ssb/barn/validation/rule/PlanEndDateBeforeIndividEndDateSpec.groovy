package no.ssb.barn.validation.rule

import no.ssb.barn.framework.ValidationContext
import no.ssb.barn.report.WarningLevel
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

import java.time.LocalDate

import static no.ssb.barn.testutil.TestDataProvider.getTestContext

class PlanEndDateBeforeIndividEndDateSpec extends Specification {

    @Subject
    PlanEndDateBeforeIndividEndDate sut

    ValidationContext context

    @SuppressWarnings('unused')
    def setup() {
        sut = new PlanEndDateBeforeIndividEndDate()
        context = getTestContext()
    }

    @Unroll
    def "Test av alle scenarier"() {
        given:
        context.rootObject.sak.sluttDato = individEndDate
        and:
        def plan = context.rootObject.sak.virksomhet[0].plan[0]
        and:
        plan.konklusjon.sluttDato = investigationEndDate
        and:
        if (removeConclusion) {
            plan.konklusjon = null
        }

        when:
        def reportEntries = sut.validate(context)

        then:
        (reportEntries != null) == errorExpected
        and:
        if (errorExpected) {
            assert 1 == reportEntries.size()
            assert WarningLevel.ERROR == reportEntries[0].warningLevel
            assert "Plan" == reportEntries[0].type
            assert reportEntries[0].errorText.contains("er etter individets sluttdato")
        }

        where:
        individEndDate                | investigationEndDate          | removeConclusion || errorExpected
        LocalDate.now().minusYears(1) | LocalDate.now()               | false            || true
        LocalDate.now().minusYears(1) | LocalDate.now()               | true             || false
        LocalDate.now()               | LocalDate.now()               | false            || false
        LocalDate.now()               | LocalDate.now().minusYears(1) | false            || false
    }
}
