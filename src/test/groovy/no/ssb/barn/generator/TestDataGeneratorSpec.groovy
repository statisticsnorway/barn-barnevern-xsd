package no.ssb.barn.generator

import no.ssb.barn.deserialize.BarnevernDeserializer
import spock.lang.Specification
import spock.lang.Subject

class TestDataGeneratorSpec extends Specification {

    @Subject
    TestDataGenerator sut

    def "when calling createInitialMutation receive instance"() {
        given:
        sut = new TestDataGenerator()

        when:
        def instance = sut.createInitialMutation()

        then:
        def xml = BarnevernDeserializer.marshallXml(instance)
        and:
        null != BarnevernDeserializer.unmarshallXml(xml)
        System.out.println(BarnevernDeserializer.unmarshallXml(xml))
    }
}
