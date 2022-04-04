package no.ssb.barn.validation.rule.measure

import no.ssb.barn.report.WarningLevel
import no.ssb.barn.validation.ValidationContext
import no.ssb.barn.xsd.*
import spock.lang.Narrative
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

import java.time.LocalDate
import java.time.ZoneId

import static no.ssb.barn.testutil.TestDataProvider.getTestContext

@Narrative("""
Tiltak Kontroll 9: Kontroll om flere plasseringstiltak er oppgitt i samme tidsperiode

Gitt at man har 2 eller flere Tiltak der Kategori/Kode er en følgende koder:<br/>
1.1, 1.2, 1.99, 2.1, 2.2, 2.3, 2.4, 2.5, 2.6, 2.99 eller 8.2<br/>
og for de tiltakene der SluttDato mangler så brukes DatoUttrekk i stedet<br/>
når tiltak1 overlapper tiltak2 med mer enn 90 dager<br/>
så gi feilmelding "Flere plasseringstiltak i samme periode (PeriodeStartDato - PeriodeSluttDato). Plasseringstiltak kan ikke overlappe med mer enn 3 måneder."

Alvorlighetsgrad: Warning
""")
class MeasureMultipleAllocationsWithinPeriodSpec extends Specification {

    @Subject
    MeasureMultipleAllocationsWithinPeriod sut

    ValidationContext context

    @SuppressWarnings('unused')
    def setup() {
        sut = new MeasureMultipleAllocationsWithinPeriod()
        context = getTestContext()
    }

    @Unroll
    def "Test av alle scenarier"() {
        given:
        context.rootObject.datoUttrekk = firstEndDate.atStartOfDay(ZoneId.of("Europe/Oslo"))
        and:
        def sak = context.rootObject.sak
        and:
        sak.tiltak.clear()
        and:
        sak.tiltak.add(createTiltakType(
                firstStartDate, firstEndDate, categoryCode, resetCancellation, resetConclusion))
        and:
        sak.tiltak.add(createTiltakType(
                secondStartDate, secondEndDate, categoryCode, resetCancellation, resetConclusion))

        when:
        def reportEntries = sut.validate(context)

        then:
        (reportEntries != null) == errorExpected
        and:
        if (errorExpected) {
            assert 2 == reportEntries.size()
            assert WarningLevel.WARNING == reportEntries[0].warningLevel
            assert reportEntries[0].errorText.contains("er mer enn 3 måneder etter")
        }

        where:
        resetCancellation | resetConclusion | firstStartDate | firstEndDate | useSecondContext | secondStartDate | secondEndDate | categoryCode || errorExpected
        // category.kode !in kodelistePlasseringstiltak
        false             | false           | getDate(0)     | getDate(0)   | true             | getDate(0)      | getDate(0)    | "~code~"     || false
        // category.kode in kodelistePlasseringstiltak && tiltak.opphevelse = null
        true              | false           | getDate(0)     | getDate(0)   | true             | getDate(0)      | getDate(0)    | "1.1"        || false

        // reset conclusion in order to use context.rootObject.datoUttrekk, error expected
        false             | true            | getDate(-3)    | getDate(0)   | true             | getDate(-3)     | getDate(0)    | "1.1"        || true
        // reset conclusion in order to use context.rootObject.datoUttrekk, no error expected
        false             | true            | getDate(-3)    | getDate(-1)  | true             | getDate(-3)     | getDate(0)    | "1.1"        || false

        false             | false           | getDate(-3)    | getDate(0)   | true             | getDate(-3)     | getDate(0)    | "1.1"        || true
        false             | false           | getDate(-3)    | getDate(0)   | true             | getDate(-2)     | getDate(0)    | "1.1"        || false
        false             | false           | getDate(-3)    | getDate(0)   | true             | getDate(-3)     | getDate(0)    | "1.1"        || true
        false             | false           | getDate(-3)    | getDate(0)   | true             | getDate(-3)     | getDate(0)    | "1.2"        || true
        false             | false           | getDate(-3)    | getDate(0)   | true             | getDate(-3)     | getDate(0)    | "1.99"       || true
        false             | false           | getDate(-3)    | getDate(0)   | true             | getDate(-3)     | getDate(0)    | "2.1"        || true
        false             | false           | getDate(-3)    | getDate(0)   | true             | getDate(-3)     | getDate(0)    | "2.2"        || true
        false             | false           | getDate(-3)    | getDate(0)   | true             | getDate(-3)     | getDate(0)    | "2.3"        || true
        false             | false           | getDate(-3)    | getDate(0)   | true             | getDate(-3)     | getDate(0)    | "2.4"        || true
        false             | false           | getDate(-3)    | getDate(0)   | true             | getDate(-3)     | getDate(0)    | "2.5"        || true
        false             | false           | getDate(-3)    | getDate(0)   | true             | getDate(-3)     | getDate(0)    | "2.6"        || true
        false             | false           | getDate(-3)    | getDate(0)   | true             | getDate(-3)     | getDate(0)    | "2.99"       || true
        false             | false           | getDate(-3)    | getDate(0)   | true             | getDate(-3)     | getDate(0)    | "8.2"        || true
    }

    static def getDate(months) {
        LocalDate.now().plusMonths(months)
    }

    def createTiltakType(
            startDate,
            endDate,
            categoryCode,
            resetCancellation,
            resetConclusion) {

        def cancellation = resetCancellation
                ? null
                : new OpphevelseType("1", "~presisering~")

        def conclusion = resetConclusion
                ? null
                : new TiltakKonklusjonType(endDate)

        new TiltakType(
                UUID.randomUUID(),
                null,
                startDate,
                new LovhjemmelType("BVL", "1", "1", ["1"], []),
                [new LovhjemmelType("BVL", "1", "1", ["1"], []), new LovhjemmelType("BVL", "1", "1", ["1"], [])],
                new KategoriType(categoryCode, null),
                [],
                [],
                cancellation,
                conclusion
        )
    }
}
