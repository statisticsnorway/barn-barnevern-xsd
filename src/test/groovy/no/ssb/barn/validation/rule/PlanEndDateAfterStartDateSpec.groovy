package no.ssb.barn.validation.rule

import no.ssb.barn.framework.ValidationContext
import no.ssb.barn.report.WarningLevel
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

import java.time.LocalDate

import static no.ssb.barn.testutil.TestDataProvider.getTestContext

class PlanEndDateAfterStartDateSpec extends Specification {

    @Subject
    PlanEndDateAfterStartDate sut

    ValidationContext context

    @SuppressWarnings('unused')
    def setup() {
        sut = new PlanEndDateAfterStartDate()
        context = getTestContext()
    }

    @Unroll
    def "Test av alle scenarier"() {
        given:
        def plan = context.rootObject.sak.virksomhet[0].plan[0]
        and:
        plan.startDato = planStartDate
        and:
        plan.konklusjon.sluttDato = planEndDate

        when:
        def reportEntries = sut.validate(context)

        then:
        (reportEntries != null) == errorExpected
        and:
        if (errorExpected) {
            assert 1 == reportEntries.size()
            assert WarningLevel.ERROR == reportEntries[0].warningLevel
            assert reportEntries[0].errorText.contains("er etter planens sluttdato ")
        }

        where:
        planStartDate                 | planEndDate                   || errorExpected
        LocalDate.now().minusYears(1) | LocalDate.now()               || false
        LocalDate.now()               | LocalDate.now()               || false
        LocalDate.now()               | LocalDate.now().minusYears(1) || true
    }
}
