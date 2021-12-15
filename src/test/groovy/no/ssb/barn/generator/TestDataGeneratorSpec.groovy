package no.ssb.barn.generator

import no.ssb.barn.converter.BarnevernConverter
import spock.lang.Specification
import spock.lang.Unroll

import java.time.LocalDate

class TestDataGeneratorSpec extends Specification {

    @Unroll
    def "when calling createInitialMutation receive valid instance"() {
        given:
        def sut = new TestDataGenerator(xmlResource)

        when:
        def instance = sut.createInitialMutation(LocalDate.now())

        then:
        noExceptionThrown()
        and:
        def xml = BarnevernConverter.marshallInstance(instance)
        and:
        null != BarnevernConverter.unmarshallXml(xml)

        where:
        xmlResource                                | _
        "/initial_mutation.xml"                    | _
        "/initial_mutation_without_virksomhet.xml" | _
        "/initial_mutation_without_melding.xml"    | _
    }

    def "when calling mutate with valid instance expect changed state"() {
        given:
        def sut = new TestDataGenerator()
        and:
        def caseEntry = new CaseEntry(
                UUID.randomUUID(),
                sut.createInitialMutation(LocalDate.now()),
                LocalDate.now(),
                1,
                currentState)

        when:
        BarnevernState.DECISION != sut.mutate(caseEntry)

        then:
        noExceptionThrown()
        and:
        expectedNewStates.contains(caseEntry.state)

        where:
        currentState                 || expectedNewStates
        BarnevernState.MESSAGE       || Set.of(BarnevernState.INVESTIGATION, BarnevernState.DECISION)
        BarnevernState.INVESTIGATION || Set.of(BarnevernState.MEASURE, BarnevernState.DECISION)
        BarnevernState.MEASURE       || Set.of(BarnevernState.PLAN, BarnevernState.DECISION, BarnevernState.AFTERCARE)
        BarnevernState.DECISION      || Set.of(BarnevernState.MEASURE, BarnevernState.DECISION, BarnevernState.AFTERCARE)
        BarnevernState.AFTERCARE     || Set.of(BarnevernState.MEASURE, BarnevernState.DECISION)
    }
}
