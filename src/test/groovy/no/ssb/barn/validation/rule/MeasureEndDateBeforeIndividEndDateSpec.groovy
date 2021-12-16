package no.ssb.barn.validation.rule

import no.ssb.barn.framework.ValidationContext
import no.ssb.barn.report.WarningLevel
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

import java.time.LocalDate

import static no.ssb.barn.testutil.TestDataProvider.getTestContext

class MeasureEndDateBeforeIndividEndDateSpec extends Specification {

    @Subject
    MeasureEndDateBeforeIndividEndDate sut

    ValidationContext context

    @SuppressWarnings('unused')
    def setup() {
        sut = new MeasureEndDateBeforeIndividEndDate()
        context = getTestContext()
    }

    @Unroll
    def "Test av alle scenarier"() {
        given:
        context.rootObject.sak.sluttDato = individEndDate
        and:
        context.rootObject.sak.virksomhet[0].tiltak[0].konklusjon.sluttDato = measureEndDate
        and:
        if (resetConclusion) {
            context.rootObject.sak.virksomhet[0].tiltak[0].konklusjon = null
        }

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
        resetConclusion | individEndDate                | measureEndDate                || errorExpected
        false           | LocalDate.now().minusYears(1) | LocalDate.now()               || true
        true            | LocalDate.now().minusYears(1) | LocalDate.now()               || false
        false           | LocalDate.now()               | LocalDate.now()               || false
        true            | LocalDate.now()               | LocalDate.now()               || false
        false           | LocalDate.now()               | LocalDate.now().minusYears(1) || false
        true            | LocalDate.now()               | LocalDate.now().minusYears(1) || false
    }
}
