package no.ssb.barn.validation.rule

import no.ssb.barn.report.WarningLevel
import no.ssb.barn.validation.ValidationContext
import spock.lang.Narrative
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

import java.time.ZonedDateTime

import static no.ssb.barn.testutil.TestDataProvider.getTestContext

@Narrative("""
Tiltak Kontroll 9: Kontroll om flere plasseringstiltak er oppgitt i samme tidsperiode

Gitt at man har 2 eller flere Tiltak der Kategori/Kode er en følgende koder:<br/>
1.1, 1.2, 1.99, 2.1, 2.2, 2.3, 2.4, 2.5, 2.6, 2.99 eller 8.2<br/>
og utled den seneste startDato (PeriodeStartDato) <br/>
utifra tiltak1 sin StartDato og tiltak2 sin StartDato<br/>
og utled den tidligste sluttDato (PeriodeSluttDato) <br/>
utifra tiltak1 sin SluttDato eller DatoUttrekk hvis tiltak1 sin SluttDato er blank og tiltak2 sin SluttDato eller DatoUttrekk hvis tiltak2 sin SluttDato er blank<br/>
når tiltak1 sin SluttDato er etter tailtakk sin StartDato og PeriodeSluttDato er mer enn 90 etter PeriodeStartDato<br/>
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
        context.rootObject.datoUttrekk = ZonedDateTime.now()
        and:
        def sak = context.rootObject.sak
        and:
        def firstMeasure = sak.tiltak[0]
        and:
        firstMeasure.startDato = firstStartDate
        and:
        firstMeasure.opphevelse.sluttDato = firstEndDate
        and:
        firstMeasure.kategori.kode = categoryCode
        and:
        if (resetRepeal) {
            firstMeasure.opphevelse = null
        }
        and:
        if (useSecondContext) {
            def secondContext = getTestContext()
            sak.tiltak[1] = secondContext.rootObject.sak.tiltak[0]

            def secondMeasure = sak.tiltak[1]

            secondMeasure.id = UUID.randomUUID()
            secondMeasure.startDato = secondStartDate
            secondMeasure.opphevelse.sluttDato = secondEndDate

            secondMeasure.kategori.kode = categoryCode

            if (resetRepeal) {
                secondMeasure.opphevelse = null
            }
        }

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
        resetRepeal | firstStartDate | firstEndDate | useSecondContext | secondStartDate | secondEndDate | categoryCode || errorExpected
        true        | getDate(-3)    | getDate(0)   | false            | null            | null          | "N/A"        || false
        false       | getDate(-3)    | getDate(0)   | false            | null            | null          | "1.1"        || false
        false       | getDate(-3)    | getDate(0)   | true             | getDate(-3)     | getDate(0)    | "1.1"        || true
        false       | getDate(-3)    | getDate(0)   | true             | getDate(-2)     | getDate(0)    | "1.1"        || false
        false       | getDate(-3)    | getDate(0)   | true             | getDate(-3)     | getDate(0)    | "~code~"     || false
        false       | getDate(-3)    | getDate(0)   | true             | getDate(-3)     | getDate(0)    | "1.1"        || true
        false       | getDate(-3)    | getDate(0)   | true             | getDate(-3)     | getDate(0)    | "1.2"        || true
        false       | getDate(-3)    | getDate(0)   | true             | getDate(-3)     | getDate(0)    | "1.99"       || true
        false       | getDate(-3)    | getDate(0)   | true             | getDate(-3)     | getDate(0)    | "2.1"        || true
        false       | getDate(-3)    | getDate(0)   | true             | getDate(-3)     | getDate(0)    | "2.2"        || true
        false       | getDate(-3)    | getDate(0)   | true             | getDate(-3)     | getDate(0)    | "2.3"        || true
        false       | getDate(-3)    | getDate(0)   | true             | getDate(-3)     | getDate(0)    | "2.4"        || true
        false       | getDate(-3)    | getDate(0)   | true             | getDate(-3)     | getDate(0)    | "2.5"        || true
        false       | getDate(-3)    | getDate(0)   | true             | getDate(-3)     | getDate(0)    | "2.6"        || true
        false       | getDate(-3)    | getDate(0)   | true             | getDate(-3)     | getDate(0)    | "2.99"       || true
        false       | getDate(-3)    | getDate(0)   | true             | getDate(-3)     | getDate(0)    | "8.2"        || true
// TODO to Roar:
//  the case of two overlapping Measures where one or neither has an endDate yet,
//  then sak.datoUttrekk needs to be used as a default endDate for the measure(s) where endDate is missing
//        true            | getDate(-4)    | getDate(0)   | true             | getDate(-4)     | getDate(0)    | "8.2"        || true

    }

    static def getDate(months) {
        ZonedDateTime.now().plusMonths(months)
    }
}
