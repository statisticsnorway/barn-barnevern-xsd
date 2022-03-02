package no.ssb.barn.validation.rule

import no.ssb.barn.validation.ValidationContext
import no.ssb.barn.report.WarningLevel
import spock.lang.Narrative
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

import java.time.LocalDate
import java.time.LocalDateTime

import static no.ssb.barn.testutil.TestDataProvider.getTestContext

@Narrative("""
Tiltak Kontroll 2c: SluttDato er etter virksomhetens SluttDato

Gitt at man har et Tiltak der Konklusjon/SluttDato finnes og i virksomhet der SluttDato finnes<br/>
når tiltakets SluttDato er etter virksomhetens SluttDato<br/>
så gi feilmeldingen "Tiltakets sluttdato {Konklusjon/SluttDato} er etter Virksomhetens sluttdato {SluttDato}"

Alvorlighetsgrad: ERROR
""")
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
        context.rootObject.sak.tiltak[0].konklusjon.sluttDato = measureEndDate
        and:
        if (resetConclusion) {
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
        resetConclusion | individEndDate                    | measureEndDate                    || errorExpected
        false           | LocalDateTime.now().minusYears(1) | LocalDateTime.now()               || true
        true            | LocalDateTime.now().minusYears(1) | LocalDateTime.now()               || false
        false           | LocalDateTime.now()               | LocalDateTime.now().minusHours(1) || false
        true            | LocalDateTime.now()               | LocalDateTime.now()               || false
        false           | LocalDateTime.now()               | LocalDateTime.now().minusYears(1) || false
        true            | LocalDateTime.now()               | LocalDateTime.now().minusYears(1) || false
    }
}
