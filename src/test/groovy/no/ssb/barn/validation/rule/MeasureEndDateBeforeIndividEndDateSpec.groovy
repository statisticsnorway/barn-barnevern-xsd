package no.ssb.barn.validation.rule

import no.ssb.barn.framework.ValidationContext
import no.ssb.barn.report.WarningLevel
import no.ssb.barn.testutil.TestDataProvider
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

import java.time.LocalDate

class MeasureEndDateBeforeIndividEndDateSpec extends Specification {

    @Subject
    MeasureEndDateBeforeIndividEndDate sut

    ValidationContext context

    @SuppressWarnings('unused')
    def setup() {
        sut = new MeasureEndDateBeforeIndividEndDate()
        context = TestDataProvider.getTestContext()
    }

    @Unroll
    def "Test av alle scenarier"() {
        given:
        context.rootObject.sak.sluttDato = individEndDate
        and:
        context.rootObject.sak.virksomhet[0].tiltak[0].konklusjon.sluttDato = measureEndDate

        when:
        def reportEntries = sut.validate(context)

        then:
        (reportEntries != null) == errorExpected
        and:
        if (errorExpected) {
            assert 1 == reportEntries.size()
            assert WarningLevel.ERROR == reportEntries[0].warningLevel
            assert reportEntries[0].errorText.contains("er etter individets sluttdato")
        }

        where:
        individEndDate                | measureEndDate                || errorExpected
        LocalDate.now().minusYears(1) | LocalDate.now()               || true
        LocalDate.now()               | LocalDate.now()               || false
        LocalDate.now()               | LocalDate.now().minusYears(1) || false
    }
}
