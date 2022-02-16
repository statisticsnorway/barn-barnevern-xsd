package no.ssb.barn.validation.rule

import no.ssb.barn.validation.ValidationContext
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
        def firstMeasure = virksomhet.tiltak[0]
        and:
        firstMeasure.startDato = firstStartDate
        and:
        firstMeasure.konklusjon.sluttDato = firstEndDate
        and:
        if (resetCategory) {
            firstMeasure.kategori = null
        } else {
            firstMeasure.kategori.kode = categoryCode
        }
        and:
        if (resetConclusion) {
            firstMeasure.konklusjon = null
        }
        and:
        if (useSecondContext) {
            def secondContext = getTestContext()
            virksomhet.tiltak[1] = secondContext.rootObject.sak.virksomhet[0].tiltak[0]

            def secondMeasure = virksomhet.tiltak[1]

            secondMeasure.id = UUID.randomUUID()
            secondMeasure.startDato = secondStartDate
            secondMeasure.konklusjon.sluttDato = secondEndDate

            if (resetCategory) {
                secondMeasure.kategori = null
            } else {
                secondMeasure.kategori.kode = categoryCode
            }

            if (resetConclusion) {
                secondMeasure.konklusjon = null
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
        resetCategory | resetConclusion | firstStartDate | firstEndDate | useSecondContext | secondStartDate | secondEndDate | categoryCode || errorExpected
        true          | false           | getDate(-3)    | getDate(0)   | false            | null            | null          | "N/A"        || false
        false         | true            | getDate(-3)    | getDate(0)   | false            | null            | null          | "N/A"        || false
        false         | false           | getDate(-3)    | getDate(0)   | false            | null            | null          | "1.1"        || false
        false         | false           | getDate(-3)    | getDate(0)   | true             | null            | getDate(0)    | "1.1"        || false
        false         | false           | getDate(-3)    | getDate(0)   | true             | getDate(-3)     | getDate(0)    | "1.1"        || true
        false         | false           | getDate(-3)    | getDate(0)   | true             | getDate(-2)     | getDate(0)    | "1.1"        || false
        false         | false           | getDate(-3)    | getDate(0)   | true             | getDate(-3)     | getDate(0)    | "~code~"     || false
        false         | false           | getDate(-3)    | getDate(0)   | true             | getDate(-3)     | getDate(0)    | "1.1"        || true
        false         | false           | getDate(-3)    | getDate(0)   | true             | getDate(-3)     | getDate(0)    | "1.2"        || true
        false         | false           | getDate(-3)    | getDate(0)   | true             | getDate(-3)     | getDate(0)    | "1.99"       || true
        false         | false           | getDate(-3)    | getDate(0)   | true             | getDate(-3)     | getDate(0)    | "2.1"        || true
        false         | false           | getDate(-3)    | getDate(0)   | true             | getDate(-3)     | getDate(0)    | "2.2"        || true
        false         | false           | getDate(-3)    | getDate(0)   | true             | getDate(-3)     | getDate(0)    | "2.3"        || true
        false         | false           | getDate(-3)    | getDate(0)   | true             | getDate(-3)     | getDate(0)    | "2.4"        || true
        false         | false           | getDate(-3)    | getDate(0)   | true             | getDate(-3)     | getDate(0)    | "2.5"        || true
        false         | false           | getDate(-3)    | getDate(0)   | true             | getDate(-3)     | getDate(0)    | "2.6"        || true
        false         | false           | getDate(-3)    | getDate(0)   | true             | getDate(-3)     | getDate(0)    | "2.99"       || true
        false         | false           | getDate(-3)    | getDate(0)   | true             | getDate(-3)     | getDate(0)    | "8.2"        || true
    }

    static def getDate(months) {
        LocalDate.now().plusMonths(months)
    }
}
