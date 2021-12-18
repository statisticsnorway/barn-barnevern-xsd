package no.ssb.barn.generator

import no.ssb.barn.xsd.BarnevernType
import spock.lang.Specification
import spock.lang.Subject

import java.time.LocalDate
import java.util.stream.StreamSupport

class SimulationSpec extends Specification {

    def NUMBER_OF_DAYS = 100
    def MIN_UPDATES_PER_DAY = 20
    def MAX_UPDATES_PER_DAY = 40
    def MAX_MUTATIONS = 2

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

    def "getRandomCaseToMutate test of success scenarios"() {
        when:
        Simulation.getRandomCaseToMutate(currentDate, caseSet)

        then:
        noExceptionThrown()

        where:
        currentDate | caseSet
        tomorrow    | Set.of(getCaseEntry(today))
        today       | Set.of(getCaseEntry(today), getCaseEntry(yesterday))
        today       | Set.of(getCaseEntry(yesterday), getCaseEntry(yesterday))
    }

    def "getRandomCaseToMutate test of failure scenarios"() {
        when:
        Simulation.getRandomCaseToMutate(currentDate, caseSet as Set<CaseEntry>)

        then:
        //noinspection GroovyUnusedAssignment
        NoSuchElementException e = thrown()

        where:
        currentDate | caseSet
        today       | Set.of()
        today       | Set.of(getCaseEntry(today))
        yesterday   | Set.of(getCaseEntry(today))
    }

    def "mutableCaseCount test of all scenarios"() {
        when:
        def result = Simulation.mutableCaseCount(currentDate, caseSet as Set<CaseEntry>)

        then:
        noExceptionThrown()
        and:
        expectedCount == result

        where:
        currentDate | caseSet                                                  || expectedCount
        today       | Set.of()                                                 || 0
        today       | Set.of(getCaseEntry(today))                              || 0
        yesterday   | Set.of(getCaseEntry(today))                              || 0
        tomorrow    | Set.of(getCaseEntry(today))                              || 1
        today       | Set.of(getCaseEntry(yesterday))                          || 1
        today       | Set.of(getCaseEntry(tomorrow))                           || 0
        today       | Set.of(getCaseEntry(today), getCaseEntry(yesterday))     || 1
        today       | Set.of(getCaseEntry(yesterday), getCaseEntry(yesterday)) || 2
    }

    static def today = LocalDate.now()
    static def yesterday = today.minusDays(1)
    static def tomorrow = today.plusDays(1)

    static def getCaseEntry(LocalDate date) {
        new CaseEntry(
                UUID.randomUUID(),
                new BarnevernType(),
                date,
                1,
                BarnevernState.MESSAGE)
    }
}
