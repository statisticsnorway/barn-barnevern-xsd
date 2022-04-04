package no.ssb.barn.validation.rule.measure

import no.ssb.barn.report.WarningLevel
import no.ssb.barn.validation.ValidationContext
import no.ssb.barn.xsd.TiltakKonklusjonType
import spock.lang.Narrative
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

import java.time.LocalDate

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
        if (resetConclusion) {
            context.rootObject.sak.tiltak[0].konklusjon = null
        } else {
            context.rootObject.sak.tiltak[0].konklusjon = new TiltakKonklusjonType(measureEndDate)
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
        resetConclusion | individEndDate | measureEndDate || errorExpected
        false       | LocalDate.now().minusYears(1) | LocalDate.now()               || true
        true        | LocalDate.now().minusYears(1) | LocalDate.now()               || false
        false       | LocalDate.now()               | LocalDate.now()               || false
        true        | LocalDate.now()               | LocalDate.now()               || false
        false       | LocalDate.now()               | LocalDate.now().minusYears(1) || false
        true        | LocalDate.now()               | LocalDate.now().minusYears(1) || false
    }
}
