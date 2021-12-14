package no.ssb.barn.generator


import spock.lang.Specification
import spock.lang.Subject

import java.time.LocalDate
import java.util.stream.StreamSupport

class SimulationSpec extends Specification {

    def NUMBER_OF_DAYS = 10

    @Subject
    Simulation sut

    def setup() {
        sut = new Simulation(
                LocalDate.now().minusDays(NUMBER_OF_DAYS),
                LocalDate.now())
    }

    def "when simulation har ran for 10 days, expect least number of cases"() {
        given:
        def expectedNumberOfMutations = Simulation.MIN_UPDATES_PER_DAY * NUMBER_OF_DAYS

        when:
        def result = sut.run()

        then:
        def splitIterator =
                Spliterators.spliteratorUnknownSize(
                        result.iterator(), Spliterator.ORDERED)
        and:
        StreamSupport.stream(splitIterator, false)
                .count() > expectedNumberOfMutations
    }
}
