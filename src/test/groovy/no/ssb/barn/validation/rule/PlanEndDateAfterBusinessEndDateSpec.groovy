package no.ssb.barn.validation.rule

import no.ssb.barn.report.WarningLevel
import no.ssb.barn.validation.ValidationContext
import spock.lang.Narrative
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

import java.time.LocalDate

import static no.ssb.barn.testutil.TestDataProvider.getTestContext

@Narrative("""
Plan Kontroll 2c: SluttDato er etter virksomhetens SluttDato

Gitt at man har en Plan der Konklusjon/SluttDato finnes og i virksomhet der SluttDato finnes<br/>
når planens SluttDato er etter virksomhetens SluttDato<br/>
så gi feilmeldingen "Planens sluttdato {Konklusjon/SluttDato} er etter virksomhetens sluttdato {SluttDato}"

Alvorlighetsgrad: ERROR
""")
class PlanEndDateAfterBusinessEndDateSpec extends Specification {

    @Subject
    PlanEndDateAfterBusinessEndDate sut

    ValidationContext context

    @SuppressWarnings('unused')
    def setup() {
        sut = new PlanEndDateAfterBusinessEndDate()
        context = getTestContext()
    }

    @Unroll
    def "Test av alle scenarier"() {
        given:
        context.rootObject.sak.virksomhet[0].sluttDato = businessEndDate
        and:
        def plan = context.rootObject.sak.virksomhet[0].plan[0]
        and:
        if (resetConclusion) {
            plan.konklusjon = null
        } else {
            plan.konklusjon.sluttDato = planEndDate
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
            assert WarningLevel.ERROR == reportEntries[0].warningLevel
            assert reportEntries[0].errorText.contains("er etter virksomhetens")
        }

        where:
        businessEndDate | planEndDate                 | resetConclusion || errorExpected
        LocalDate.now() | LocalDate.now()             | false           || false
        LocalDate.now() | LocalDate.now().plusDays(1) | false           || true
        LocalDate.now() | LocalDate.now().plusDays(1) | true            || false
    }
}
