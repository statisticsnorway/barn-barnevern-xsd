package no.ssb.barn.validation.rule.measure

import no.ssb.barn.report.WarningLevel
import no.ssb.barn.validation.ValidationContext
import spock.lang.*

import java.time.LocalDate

import static no.ssb.barn.testutil.TestDataProvider.getTestContext

@Narrative("""
Tiltak Kontroll 2e: StartDato er før sakens StartDato

Gitt at man har et Tiltak der StartDato finnes og sak der StartDato finnes<br/>
når tiltakets StartDato er før sakens StartDato <br/>
så gi feilmeldingen "Tiltakets startdato {StartDato} er før sakens startdato {StartDato}"

Alvorlighetsgrad: ERROR
""")
class MeasureStartDateBeforeCaseStartDateSpec extends Specification {

    @Subject
    MeasureStartDateBeforeCaseStartDate sut
    ValidationContext context

    @SuppressWarnings('unused')
    def setup() {
        sut = new MeasureStartDateBeforeCaseStartDate()
        context = getTestContext()
    }

    @Unroll
    def "Test av alle scenarier"() {
        given:
        context.rootObject.sak.startDato = businessStartDate
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
            assert reportEntries[0].errorText.contains("er før sakens startdato")
        }

        where:
        businessStartDate             | measureStartDate              || errorExpected
        LocalDate.now().minusYears(1) | LocalDate.now()               || false
        LocalDate.now()               | LocalDate.now()               || false
        LocalDate.now()               | LocalDate.now().minusYears(1) || true
    }
}
