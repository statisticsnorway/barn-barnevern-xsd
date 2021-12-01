package no.ssb.barn.validation.rule

import no.ssb.barn.framework.ValidationContext
import no.ssb.barn.report.WarningLevel
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

import java.time.LocalDate

import static no.ssb.barn.testutil.TestDataProvider.getTestContext

class MeasureMultipleAllocationsWithinPeriodSpec extends Specification {

    @Subject
    MeasureMultipleAllocationsWithinPeriod sut

    ValidationContext context
    ValidationContext secondContext

    @SuppressWarnings('unused')
    def setup() {
        sut = new MeasureMultipleAllocationsWithinPeriod()
        context = getTestContext()
    }

    @Unroll
    def "Test av alle scenarier"() {
        given:
        def virksomhet = context.rootObject.sak.virksomhet[0]
        and:
        virksomhet.tiltak[0].startDato = firstStartDate
        and:
        virksomhet.tiltak[0].konklusjon.sluttDato = firstEndDate
        and:
        virksomhet.tiltak[0].kategori.kode = categoryCode
        and:
        if (useSecond) {
            secondContext = getTestContext()
            virksomhet.tiltak[1] = secondContext.rootObject.sak.virksomhet[0].tiltak[0]
            virksomhet.tiltak[1].id = "2"
            virksomhet.tiltak[1].startDato = secondStartDate
            virksomhet.tiltak[1].konklusjon.sluttDato = secondEndDate
            virksomhet.tiltak[1].kategori.kode = categoryCode
        }
        and:

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
        firstStartDate | firstEndDate | useSecond | secondStartDate | secondEndDate | categoryCode || errorExpected
        getDate(-3)    | getDate(0)   | false     | null            | null          | "1.1"        || false
        getDate(-3)    | getDate(0)   | true      | null            | getDate(0)    | "1.1"        || false
        getDate(-3)    | getDate(0)   | true      | getDate(-3)     | getDate(0)    | "1.1"        || true
        getDate(-3)    | getDate(0)   | true      | getDate(-2)     | getDate(0)    | "1.1"        || false
        getDate(-3)    | getDate(0)   | true      | getDate(-3)     | getDate(0)    | "~code~"     || false
        getDate(-3)    | getDate(0)   | true      | getDate(-3)     | getDate(0)    | "1.1"        || true
        getDate(-3)    | getDate(0)   | true      | getDate(-3)     | getDate(0)    | "1.2"        || true
        getDate(-3)    | getDate(0)   | true      | getDate(-3)     | getDate(0)    | "1.99"       || true
        getDate(-3)    | getDate(0)   | true      | getDate(-3)     | getDate(0)    | "2.1"        || true
        getDate(-3)    | getDate(0)   | true      | getDate(-3)     | getDate(0)    | "2.2"        || true
        getDate(-3)    | getDate(0)   | true      | getDate(-3)     | getDate(0)    | "2.3"        || true
        getDate(-3)    | getDate(0)   | true      | getDate(-3)     | getDate(0)    | "2.4"        || true
        getDate(-3)    | getDate(0)   | true      | getDate(-3)     | getDate(0)    | "2.5"        || true
        getDate(-3)    | getDate(0)   | true      | getDate(-3)     | getDate(0)    | "2.6"        || true
        getDate(-3)    | getDate(0)   | true      | getDate(-3)     | getDate(0)    | "2.99"       || true
        getDate(-3)    | getDate(0)   | true      | getDate(-3)     | getDate(0)    | "8.2"        || true
    }

    static def getDate(months) {
        LocalDate.now().plusMonths(months)
    }
}
