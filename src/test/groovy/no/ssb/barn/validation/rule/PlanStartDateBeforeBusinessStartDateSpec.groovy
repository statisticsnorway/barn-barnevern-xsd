package no.ssb.barn.validation.rule

import no.ssb.barn.report.WarningLevel
import no.ssb.barn.validation.ValidationContext
import spock.lang.Ignore
import spock.lang.Narrative
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

import java.time.LocalDate
import java.time.LocalDateTime

import static no.ssb.barn.testutil.TestDataProvider.getTestContext

@Narrative("""
Plan Kontroll 2e: StartDato er før virksomhetens StartDato

Gitt at man har en Plan der StartDato finnes og virksomhet der StartDato finnes<br/>
når planens StartDato er før virksomhetens StartDato <br/>
så gi feilmeldingen "Planens startdato {StartDato } er før virksomhetens startdato {StartDato }"

Alvorlighetsgrad: ERROR
""")
class PlanStartDateBeforeBusinessStartDateSpec extends Specification {

    @Subject
    PlanStartDateBeforeBusinessStartDate sut

    ValidationContext context

    @SuppressWarnings('unused')
    def setup() {
        sut = new PlanStartDateBeforeBusinessStartDate()
        context = getTestContext()
    }

    @Unroll
    @Ignore("Fix me")
    def "Test av alle scenarier"() {
        given:
        context.rootObject.sak.startDato = businessStartDate
        and:
        context.rootObject.sak.plan[0].startDato = planStartDate

        when:
        def reportEntries = sut.validate(context)

        then:
        (reportEntries != null) == errorExpected
        and:
        if (errorExpected) {
            assert 1 == reportEntries.size()
            assert WarningLevel.ERROR == reportEntries[0].warningLevel
            assert reportEntries[0].errorText.contains("er før virksomhetens startdato")
        }

        where:
        businessStartDate | planStartDate || errorExpected
        LocalDateTime.now().minusYears(1) | LocalDateTime.now() || false
        LocalDateTime.now()               | LocalDateTime.now()               || false
        LocalDateTime.now()               | LocalDateTime.now().minusYears(1) || true
    }
}
