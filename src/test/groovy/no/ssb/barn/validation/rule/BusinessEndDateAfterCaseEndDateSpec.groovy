package no.ssb.barn.validation.rule

import no.ssb.barn.report.WarningLevel
import no.ssb.barn.validation.ValidationContext
import spock.lang.*

import java.time.LocalDateTime

import static no.ssb.barn.testutil.TestDataProvider.getTestContext

@Narrative("""
Virksomhet Kontroll 2c: SluttDato er etter sakens SluttDato

Gitt at man har en sak der SluttDato finnes og virksomhet der SluttDato finnes<br/>
når virksomhetens SluttDato er etter sakens SluttDato<br/>
så gi feilmeldingen "Virksomhetens startdato {SluttDato} er etter sakens sluttdato {SluttDato}"

Alvorlighetsgrad: ERROR
""")
class BusinessEndDateAfterCaseEndDateSpec extends Specification {

    @Subject
    BusinessEndDateAfterCaseEndDate sut

    ValidationContext context

    @SuppressWarnings('unused')
    def setup() {
        sut = new BusinessEndDateAfterCaseEndDate()
        context = getTestContext()
    }

    @Unroll("caseEndDate: #caseEndDate, businessEndDate #businessEndDate")
    @Ignore("Fix me")
    def "Test av alle scenarier"() {
        given:
        context.rootObject.sak.sluttDato = caseEndDate
        and:
        context.rootObject.sak.virksomhet[0].sluttDato = businessEndDate

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
            assert reportEntries[0].errorText.contains("er etter sakens")
        }

        where:
        caseEndDate                       | businessEndDate                   || errorExpected
        LocalDateTime.now()               | LocalDateTime.now().minusYears(1) || false
        LocalDateTime.now()               | null                              || false
        LocalDateTime.now().minusYears(1) | LocalDateTime.now()               || true
    }
}
