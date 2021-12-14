package no.ssb.barn.generator

import no.ssb.barn.converter.BarnevernConverter
import spock.lang.Specification
import spock.lang.Subject

class TestDataGeneratorSpec extends Specification {

    @Subject
    TestDataGenerator sut

    def setup() {
        sut = new TestDataGenerator()
    }

    def "when calling createInitialMutation receive valid instance"() {
        when:
        def instance = sut.createInitialMutation()

        then:
        def xml = BarnevernConverter.marshallInstance(instance)
        and:
        null != BarnevernConverter.unmarshallXml(xml)
    }

    def "when calling mutate with valid instance receive mutated instance"() {
        expect:
        null != sut.mutate(sut.createInitialMutation())
    }
}
