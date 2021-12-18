package no.ssb.barn.generator

import no.ssb.barn.xsd.BarnevernType
import spock.lang.Specification
import spock.lang.Subject

import java.time.LocalDate
import java.util.stream.StreamSupport

class SimulationSpec extends Specification {

    def NUMBER_OF_DAYS = 10
    def MIN_UPDATES_PER_DAY = 4
    def MAX_UPDATES_PER_DAY = 8
    def MAX_MUTATIONS = 4

    @Subject
    Simulation sut

    def setup() {
        sut = new Simulation(
                MIN_UPDATES_PER_DAY,
                MAX_UPDATES_PER_DAY,
                MAX_MUTATIONS)
    }

    def "when running for a single day, expect generated cases"() {
        when:
        def result = sut.run(0)

        then:
        def splitIterator =
                Spliterators.spliteratorUnknownSize(
                        result.iterator(), Spliterator.ORDERED)
        and:
        StreamSupport.stream(splitIterator, false)
                .count() > 0
    }

    def "when simulation har ran for 10 days, expect least number of cases"() {
        given:
        def expectedLeastNumberOfMutations = MIN_UPDATES_PER_DAY * NUMBER_OF_DAYS

        when:
        def result = sut.run(NUMBER_OF_DAYS)

        then:
        def splitIterator =
                Spliterators.spliteratorUnknownSize(
                        result.iterator(), Spliterator.ORDERED)
        and:
        StreamSupport.stream(splitIterator, false)
                .count() > expectedLeastNumberOfMutations
    }

    def "when calling secondary constructor, no exception is thrown"() {
        when:
        sut = new Simulation()

        then:
        noExceptionThrown()
    }

    def "mutableCaseCount test of all scenarios"() {
        when:
        def result = Simulation.mutableCaseCount(LocalDate.now(), caseSet as Set<CaseEntry>)

        then:
        noExceptionThrown()
        and:
        expectedCount == result

        where:
        caseSet                                            || expectedCount
        Set.of()                                           || 0
        Set.of(getCaseEntry(LocalDate.now()))              || 0
        Set.of(getCaseEntry(LocalDate.now().minusDays(1))) || 1
    }

    static def getCaseEntry(LocalDate date) {
        new CaseEntry(
                UUID.randomUUID(),
                new BarnevernType(),
                date,
                1,
                BarnevernState.MESSAGE)
    }
}
