package no.ssb.barn.validation.rule

import no.ssb.barn.validation.ValidationContext
import no.ssb.barn.report.WarningLevel
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

import java.time.LocalDate

import static no.ssb.barn.testutil.TestDataProvider.getTestContext

class CaseEndDateAfterStartDateSpec extends Specification {

    @Subject
    CaseEndDateAfterStartDate sut

    ValidationContext context

    @SuppressWarnings('unused')
    def setup() {
        sut = new CaseEndDateAfterStartDate()
        context = getTestContext()
    }

    @Unroll("startDate: #startDate, endDate #endDate")
    def "Test av alle scenarier"() {
        given:
        context.rootObject.sak.startDato = startDate
        and:
        context.rootObject.sak.sluttDato = endDate

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
            assert reportEntries[0].errorText.contains("er etter sluttdato")
        }

        where:
        startDate                     | endDate                       || errorExpected
        LocalDate.now().minusYears(1) | LocalDate.now()               || false
        LocalDate.now()               | null                          || false
        LocalDate.now()               | LocalDate.now().minusYears(1) || true
    }
}
