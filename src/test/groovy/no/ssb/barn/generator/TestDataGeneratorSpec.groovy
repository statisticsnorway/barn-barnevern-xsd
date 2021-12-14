package no.ssb.barn.generator

import no.ssb.barn.converter.BarnevernConverter
import spock.lang.Specification
import spock.lang.Unroll

class TestDataGeneratorSpec extends Specification {

    @Unroll
    def "when calling createInitialMutation receive valid instance"() {
        given:
        def sut = new TestDataGenerator(xmlResource)

        when:
        def instance = sut.createInitialMutation()

        then:
        def xml = BarnevernConverter.marshallInstance(instance)
        and:
        null != BarnevernConverter.unmarshallXml(xml)

        where:
        xmlResource | _
        "/initial_mutation.xml" | _
        "/initial_mutation_without_virksomhet.xml" | _
        "/initial_mutation_without_melding.xml" | _
    }

    def "when calling mutate with valid instance receive mutated instance"() {
        given:
        def sut = new TestDataGenerator()

        expect:
        null != sut.mutate(sut.createInitialMutation())
    }
}
