package no.ssb.barn.validation.rule

import no.ssb.barn.report.WarningLevel
import no.ssb.barn.validation.ValidationContext
import spock.lang.*

import java.time.LocalDateTime

import static no.ssb.barn.testutil.TestDataProvider.getTestContext

@Narrative("""
Virksomhet Kontroll 2e: StartDato er før sakens StartDato

Gitt at man har en sak der StartDato finnes og virksomhet der StartDato finnes<br/>
når virksomhetens StartDato er før sakens StartDato <br/>
så gi feilmeldingen "Virksomhetens startdato {StartDato } er før sakens startdato {StartDato }"

Alvorlighetsgrad: ERROR
""")
class BusinessStartDateBeforeCaseStartDateSpec extends Specification {

    @Subject
    BusinessStartDateBeforeCaseStartDate sut

    ValidationContext context

    @SuppressWarnings('unused')
    def setup() {
        sut = new BusinessStartDateBeforeCaseStartDate()
        context = getTestContext()
    }

    @Unroll("caseStartDate: #caseStartDate, businessStartDate #businessStartDate")
    @Ignore("Fix me")
    def "Test av alle scenarier"() {
        given:
        context.rootObject.sak.startDato = caseStartDate
        and:
        context.rootObject.sak.virksomhet[0].startDato = businessStartDate

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
            assert reportEntries[0].errorText.contains("er før sakens")
        }

        where:
        caseStartDate                     | businessStartDate                 || errorExpected
        LocalDateTime.now().minusYears(1) | LocalDateTime.now()               || false
        LocalDateTime.now()               | LocalDateTime.now().minusYears(1) || true
    }
}
