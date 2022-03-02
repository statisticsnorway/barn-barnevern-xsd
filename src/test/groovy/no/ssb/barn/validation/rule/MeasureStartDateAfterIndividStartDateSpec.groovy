package no.ssb.barn.validation.rule

import no.ssb.barn.report.WarningLevel
import no.ssb.barn.validation.ValidationContext
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

import java.time.LocalDateTime

import static no.ssb.barn.testutil.TestDataProvider.getTestContext

class MeasureStartDateAfterIndividStartDateSpec extends Specification {

    @Subject
    MeasureStartDateAfterIndividStartDate sut

    ValidationContext context

    @SuppressWarnings('unused')
    def setup() {
        sut = new MeasureStartDateAfterIndividStartDate()
        context = getTestContext()
    }

    @Unroll
    def "Test av alle scenarier"() {
        given:
        context.rootObject.sak.startDato = individStartDate
        and:
        context.rootObject.sak.tiltak[0].startDato = measureStartDate

        when:
        def reportEntries = sut.validate(context)

        then:
        (reportEntries != null) == errorExpected
        and:
        if (errorExpected) {
            assert 1 == reportEntries.size()
            assert WarningLevel.ERROR == reportEntries[0].warningLevel
            assert reportEntries[0].errorText.contains("være lik eller etter individets startdato")
        }

        where:
        individStartDate                  | measureStartDate                  || errorExpected
        LocalDateTime.now().minusYears(1) | LocalDateTime.now()               || false
        LocalDateTime.now()               | LocalDateTime.now()               || false
        LocalDateTime.now()               | LocalDateTime.now().minusYears(1) || true
    }
}
