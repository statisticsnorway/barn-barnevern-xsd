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
        if (resetCategory) {
            virksomhet.tiltak[0].kategori = null
        } else {
            virksomhet.tiltak[0].kategori.kode = categoryCode
        }
        and:
        if (useSecond) {
            def secondContext = getTestContext()
            virksomhet.tiltak[1] = secondContext.rootObject.sak.virksomhet[0].tiltak[0]
            virksomhet.tiltak[1].id = UUID.randomUUID()
            virksomhet.tiltak[1].startDato = secondStartDate
            virksomhet.tiltak[1].konklusjon.sluttDato = secondEndDate
            if (resetCategory) {
                virksomhet.tiltak[1].kategori = null
            } else {
                virksomhet.tiltak[1].kategori.kode = categoryCode
            }
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
        resetCategory | firstStartDate | firstEndDate | useSecond | secondStartDate | secondEndDate | categoryCode || errorExpected
        true          | getDate(-3)    | getDate(0)   | false     | null            | null          | "N/A"        || false
        false         | getDate(-3)    | getDate(0)   | false     | null            | null          | "1.1"        || false
        false         | getDate(-3)    | getDate(0)   | true      | null            | getDate(0)    | "1.1"        || false
        false         | getDate(-3)    | getDate(0)   | true      | getDate(-3)     | getDate(0)    | "1.1"        || true
        false         | getDate(-3)    | getDate(0)   | true      | getDate(-2)     | getDate(0)    | "1.1"        || false
        false         | getDate(-3)    | getDate(0)   | true      | getDate(-3)     | getDate(0)    | "~code~"     || false
        false         | getDate(-3)    | getDate(0)   | true      | getDate(-3)     | getDate(0)    | "1.1"        || true
        false         | getDate(-3)    | getDate(0)   | true      | getDate(-3)     | getDate(0)    | "1.2"        || true
        false         | getDate(-3)    | getDate(0)   | true      | getDate(-3)     | getDate(0)    | "1.99"       || true
        false         | getDate(-3)    | getDate(0)   | true      | getDate(-3)     | getDate(0)    | "2.1"        || true
        false         | getDate(-3)    | getDate(0)   | true      | getDate(-3)     | getDate(0)    | "2.2"        || true
        false         | getDate(-3)    | getDate(0)   | true      | getDate(-3)     | getDate(0)    | "2.3"        || true
        false         | getDate(-3)    | getDate(0)   | true      | getDate(-3)     | getDate(0)    | "2.4"        || true
        false         | getDate(-3)    | getDate(0)   | true      | getDate(-3)     | getDate(0)    | "2.5"        || true
        false         | getDate(-3)    | getDate(0)   | true      | getDate(-3)     | getDate(0)    | "2.6"        || true
        false         | getDate(-3)    | getDate(0)   | true      | getDate(-3)     | getDate(0)    | "2.99"       || true
        false         | getDate(-3)    | getDate(0)   | true      | getDate(-3)     | getDate(0)    | "8.2"        || true
    }

    static def getDate(months) {
        LocalDate.now().plusMonths(months)
    }
}
