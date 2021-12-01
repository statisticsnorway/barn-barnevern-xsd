package no.ssb.barn.validation.rule

import no.ssb.barn.framework.ValidationContext
import no.ssb.barn.report.WarningLevel
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

import java.time.LocalDate

import static no.ssb.barn.testutil.TestDataProvider.getTestContext

class MeasureEndDateAfterStartDateSpec extends Specification {

    @Subject
    MeasureEndDateAfterStartDate sut

    ValidationContext context

    @SuppressWarnings('unused')
    def setup() {
        sut = new MeasureEndDateAfterStartDate()
        context = getTestContext()
    }

    @Unroll
    def "Test av alle scenarier"() {
        given:
        def measure = context.rootObject.sak.virksomhet[0].tiltak[0]
        and:
        measure.startDato = measureStartDate
        and:
        measure.konklusjon.sluttDato = measureEndDate
        and:
        if (removeConclusion) {
            measure.konklusjon = null
        }

        when:
        def reportEntries = sut.validate(context)

        then:
        (reportEntries != null) == errorExpected
        and:
        if (errorExpected) {
            assert 1 == reportEntries.size()
            assert WarningLevel.ERROR == reportEntries[0].warningLevel
            assert reportEntries[0].errorText.contains("for tiltaket er etter sluttdato")
        }

        where:
        measureStartDate              | measureEndDate                | removeConclusion || errorExpected
        LocalDate.now().minusYears(1) | LocalDate.now()               | false            || false
        LocalDate.now()               | LocalDate.now()               | false            || false
        LocalDate.now()               | LocalDate.now().minusYears(1) | false            || true
        LocalDate.now()               | LocalDate.now().minusYears(1) | true             || false
    }
}
