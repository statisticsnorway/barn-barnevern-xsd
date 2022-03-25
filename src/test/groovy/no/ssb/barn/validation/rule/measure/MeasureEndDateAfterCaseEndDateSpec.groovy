package no.ssb.barn.validation.rule.measure

import no.ssb.barn.report.WarningLevel
import no.ssb.barn.validation.ValidationContext
import spock.lang.Narrative
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

import java.time.ZonedDateTime

import static no.ssb.barn.testutil.TestDataProvider.getTestContext

@Narrative("""
Tiltak Kontroll 2c: SluttDato er etter virksomhetens SluttDato

Gitt at man har et Tiltak der Konklusjon/SluttDato finnes og i virksomhet der SluttDato finnes<br/>
når tiltakets SluttDato er etter virksomhetens SluttDato<br/>
så gi feilmeldingen "Tiltakets sluttdato {Konklusjon/SluttDato} er etter Virksomhetens sluttdato {SluttDato}"

Alvorlighetsgrad: ERROR
""")
class MeasureEndDateAfterCaseEndDateSpec extends Specification {

    @Subject
    MeasureEndDateAfterCaseEndDate sut

    ValidationContext context

    @SuppressWarnings('unused')
    def setup() {
        sut = new MeasureEndDateAfterCaseEndDate()
        context = getTestContext()
    }

    @Unroll
    def "Test av alle scenarier"() {
        given:
        context.rootObject.sak.sluttDato = individEndDate
        and:
        context.rootObject.sak.tiltak[0].konklusjon.sluttDato = measureEndDate
        and:
        if (resetRepeal) {
            context.rootObject.sak.tiltak[0].konklusjon = null
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
        resetRepeal | individEndDate | measureEndDate || errorExpected
        false           | ZonedDateTime.now().minusYears(1) | ZonedDateTime.now()               || true
        true            | ZonedDateTime.now().minusYears(1) | ZonedDateTime.now()               || false
        false           | ZonedDateTime.now()               | ZonedDateTime.now().minusHours(1) || false
        true            | ZonedDateTime.now()               | ZonedDateTime.now()               || false
        false           | ZonedDateTime.now()               | ZonedDateTime.now().minusYears(1) || false
        true            | ZonedDateTime.now()               | ZonedDateTime.now().minusYears(1) || false
    }
}
