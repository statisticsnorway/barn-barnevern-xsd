package no.ssb.barn.validation.rule.measure

import no.ssb.barn.report.WarningLevel
import no.ssb.barn.validation.ValidationContext
import spock.lang.Narrative
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

import java.time.LocalDate

import static no.ssb.barn.testutil.TestDataProvider.getTestContext

@Narrative("""
Tiltak Kontroll 2a: StartDato er etter SluttDato

Gitt at man har et Tiltak der StartDato og Konklusjon/SluttDato finnes<br/>
når StartDato er etter SluttDato<br/>
så gi feilmeldingen "Tiltakets startdato {StartDato} er etter sluttdato {Konklusjon/SluttDato}"

Alvorlighetsgrad: ERROR
""")
class MeasureStartDateAfterEndDateSpec extends Specification {

    @Subject
    MeasureStartDateAfterEndDate sut

    ValidationContext context

    @SuppressWarnings('unused')
    def setup() {
        sut = new MeasureStartDateAfterEndDate()
        context = getTestContext()
    }

    @Unroll
    def "Test av alle scenarier"() {
        given:
        def measure = context.rootObject.sak.tiltak[0]
        and:
        measure.startDato = measureStartDate
        and:
        measure.konklusjon.sluttDato = measureEndDate
        and:
        if (removeRepeal) {
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
        measureStartDate              | measureEndDate                | removeRepeal || errorExpected
        LocalDate.now().minusYears(1) | LocalDate.now()               | false        || false
        LocalDate.now()               | LocalDate.now()               | false        || false
        LocalDate.now()               | LocalDate.now().minusYears(1) | false        || true
        LocalDate.now()               | LocalDate.now().minusYears(1) | true         || false
    }
}
